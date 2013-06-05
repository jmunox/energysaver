/*
 * UnsignedCharValue.h
 *
 *
 *  This file is part of the "Plugwise Module for CRN Toolbox".
 *
 * 	"Plugwise Module for CRN Toolbox" is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 *
 * 	"Plugwise Module for CRN Toolbox" is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * 	GNU General Public License for more details.
 *
 * 	You should have received a copy of the GNU General Public License
 * 	along with "Plugwise Module for CRN Toolbox". If not, see <http://www.gnu.org/licenses/>
 *
 *
 * 	"Plugwise Module for CRN Toolbox" is based on the protocol information provided by
 * 	<a href="http://www.maartendamen.com/category/plugwise-unleashed/">Maarten Damen</a> and
 * 	<a href="http://roheve.wordpress.com/2011/04/24/plugwise-protocol-analyse/">Roheve</a>;
 * 	and the code implementations from
 * 	<a href="https://github.com/gonium/libplugwise">Gonium's libplugwise</a>, and
 *  <a href="http://www.cs.stir.ac.uk/~kjt/software/comms/plugwise.html"> Kenneth J. Turner's work</a>.
 *
 *	@author Jesus Muñoz-Alcantara
 *	@version alpha.6.0: 17/02/2012
 */


#ifndef UNSIGNEDCHARVALUE_H_
#define UNSIGNEDCHARVALUE_H_

#include "../core/Value.h"
#include <string.h>
#include <sstream>


class UnsignedCharValue: public Value {

	//GIBN_MAKE_AVAILABLE( UnsignedCharValue, Value )

public:
	UnsignedCharValue();
	UnsignedCharValue( const unsigned char* d, bool valid );
	UnsignedCharValue( const unsigned char* d );
	UnsignedCharValue( std::string s );
	UnsignedCharValue( int i );
	virtual ~UnsignedCharValue();

	/// Clone this object
	virtual Value* clone() const;

	//access (read)
	virtual int getInt() const;
	virtual float getFloat() const;
	virtual fix getFix() const;
	virtual const unsigned char* getChar();
	std::string getString() const;
	/// Print object to stream o.
	virtual void toString( std::ostream &o ) const;
	std::string getHexString() const;


	//access (write)
	virtual void setVal( int k );
	virtual void setVal( float f );
	virtual void setVal( const unsigned char* d );
	virtual void setVal( std::string s );

	//math ops
	virtual Value& operator+=( const Value& v );
	virtual Value& operator-=( const Value& v );
	virtual Value& operator*=( const Value& v );
	virtual Value& operator/=( const Value& v );
	virtual Value& operator+=( const int v );
	virtual Value& operator-=( const int v );
	virtual Value& operator*=( const int v );
	virtual Value& operator/=( const int v );

	virtual Value& sqrt();	///< square root
	virtual Value& log2();	///< Logarithm to base 2.
	virtual Value& exp2();	///< Raise 2 to-the-power of this value.
	virtual Value& exp();	///< Raise e to-the-power of thie value.
	virtual Value& sin();	///< Sine
	virtual Value& cos();	///< Cosine
	virtual Value& tan();	///< Tangent
	virtual Value& asin();	///< Arc-Sine
	virtual Value& acos();	///< Arc-Cosine
	virtual Value& atan();	///< Arc-Tangent
	virtual Value& abs();	///< ABS

	//comparison ops
	virtual bool operator<( const Value& v ) const;
	virtual bool operator>( const Value& v ) const;
	virtual bool operator==( const Value& v ) const;
	virtual bool operator!=( const Value& v ) const;
	virtual bool operator<=( const Value& v ) const;
	virtual bool operator>=( const Value& v ) const;

	int length() { return strlen((char*)val); }


	private:
		const unsigned char* val;

};


#endif /* UNSIGNEDCHARVALUE_H_ */
