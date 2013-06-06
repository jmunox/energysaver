package org.moxhu.util.db.mongodb;

import java.util.NoSuchElementException;

import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.collections.list.CursorableLinkedList;

/**
 * 
 * @author Jesus
 * 
 */

public class NonBlockingPool extends BaseObjectPool {

	/**
	 * The default cap on the total number of active instances from the pool.
	 * 
	 * @see #getMax
	 */
	public static final int DEFAULT_MAX = 8;

	// --- private attributes ---------------------------------------

	/**
	 * The cap on the number of idle instances in the pool.
	 * 
	 * @see #setMax
	 * @see #getMax
	 */
	private int _max = DEFAULT_MAX;

	/**
	 * The number of objects {@link #borrowObject} borrowed from the pool
	 */
	private int _numActive = 0;

	/** My pool. */
	private CursorableLinkedList _pool = null;

	/** My {@link PoolableObjectFactory}. */
	private PoolableObjectFactory _factory = null;

	public NonBlockingPool(PoolableObjectFactory factory) {
		this(factory, DEFAULT_MAX);
	}

	public NonBlockingPool(PoolableObjectFactory factory, int max) {
		_factory = factory;
		_max = max;
		_pool = new CursorableLinkedList();
	}

	@Override
	public Object borrowObject() throws Exception {

		assertOpen();
		// if there are any sleeping, just grab one of those
		Object object = null;
		boolean newlyCreated = false;
		// check if we can create one
		synchronized (this) {
			if (_max < 0 || _numActive < _max) {
				newlyCreated = true;
				object = _factory.makeObject();
				_pool.addLast(object);
				_numActive++;
			} else {
				try {
					newlyCreated = false;
					object = _pool.removeFirst();
					_pool.addLast(object);
				} catch (NoSuchElementException e) {
					/* ignored */
				}
			}
		}
		try {
			_factory.activateObject(object);
		} catch (Throwable e) {
			// object cannot be activated or is invalid
			try {
				_factory.destroyObject(object);
			} catch (Throwable e2) {
				// cannot destroy broken object
			}
			synchronized (this) {
				_numActive--;
				notifyAll();
			}
			if (newlyCreated) {
				throw new NoSuchElementException(
				        "Could not create a validated object, cause: "
				                + e.getMessage());
			}
		}

		return object;
		// activate & validate the object
	}

	@Override
	public void invalidateObject(Object arg0) throws Exception {
		try {
			_factory.destroyObject(arg0);
		} catch (Throwable e2) {
			// cannot destroy broken object
		}
		synchronized (this) {
			_numActive--;
			notifyAll();
		}

	}

	@Override
	public void returnObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns the cap on the number of instances in the pool.
	 * 
	 * @return the cap on the number of instances in the pool.
	 * @see #setMax
	 */
	public synchronized int getMax() {
		return _max;
	}

	/**
	 * Sets the cap on the number of instances in the pool.
	 * 
	 * @param maxIdle
	 *            The cap on the number of instances in the pool. Use a negative
	 *            value to indicate an unlimited number of instances.
	 * @see #getMax
	 */
	public synchronized void setMax(int max) {
		_max = max;
		notifyAll();
	}

}
