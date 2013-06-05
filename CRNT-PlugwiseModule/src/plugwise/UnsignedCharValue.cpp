/*
 * UnsignedCharValue.cpp
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


#include "UnsignedCharValue.h"
#include <sstream>
#include <iostream>


UnsignedCharValue::UnsignedCharValue() : val( 0 ) {}

UnsignedCharValue::UnsignedCharValue( const unsigned char* d, bool valid ) : Value( valid ), val( d ) {}

UnsignedCharValue::~UnsignedCharValue() {
	// TODO Auto-generated destructor stub
}

UnsignedCharValue::UnsignedCharValue( const unsigned char* d ){

	val = d;
}


UnsignedCharValue::UnsignedCharValue( std::string s ){
	//log("val s: ")<< s << std::endl;
	val = reinterpret_cast<const unsigned char*>(s.c_str());
	//log("val uc: ")<< val<< std::endl;
}

UnsignedCharValue::UnsignedCharValue( int i ) {
	val = reinterpret_cast<const unsigned char*>(i);
}


Value* UnsignedCharValue::clone() const {
	return new UnsignedCharValue( *this );
}



// read access
int UnsignedCharValue::getInt() const {
	//log( "getInt() WARNING: operation not permitted on class UnsignedCharValue!" );
	return 0;
}

float UnsignedCharValue::getFloat() const {
	//log( "getFloat() WARNING: operation not permitted on class UnsignedCharValue!" );
	return 0.0;
}

fix UnsignedCharValue::getFix() const {
	//log( "getFix() WARNING: operation not permitted on class UnsignedCharValue!" );
	return 0;
}

const unsigned char* UnsignedCharValue::getChar(){
//	log("val: ")<< val<< std::endl;
	return val;
}

std::string UnsignedCharValue::getHexString() const {
	std::ostringstream my_str;
		my_str << std::hex << std::uppercase << val;
		return my_str.str();
}

std::string UnsignedCharValue::getString() const {
//	std::ostringstream my_str;
//		my_str << val;
//		return my_str.str();
	std::string mystring((char*)val);
	return mystring;
}

void UnsignedCharValue::toString( std::ostream &o ) const {
	o << val;
}



// write access
void UnsignedCharValue::setVal( std::string s ) {
		val = reinterpret_cast<const unsigned char*>(s.c_str());
}

void UnsignedCharValue::setVal( const unsigned char* d ) {
	val = d;
}


void UnsignedCharValue::setVal( int i ) {
	val = reinterpret_cast<const unsigned char*>(i);
}

void UnsignedCharValue::setVal( float f ) {
	//log( "setVal(float) WARNING: operation not permitted on class UnsignedCharValue!" );
}


// not permitted operations
Value& UnsignedCharValue::operator+=( const Value& v ) {
	//log( "WARNING: operation '+=' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::operator-=( const Value& v ) {
	//log( "WARNING: operation '-=' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::operator*=( const Value& v ) {
	//log( "WARNING: operation '*=' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::operator/=( const Value& v ) {
	//log( "WARNING: operation '/=' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::operator+=( const int v ) {
	//log( "WARNING: operation '+=' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::operator-=( const int v ) {
	//log( "WARNING: operation '-=' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::operator*=( const int v ) {
	//log( "WARNING: operation '*=' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::operator/=( const int v ) {
	//log( "WARNING: operation '/=' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::sqrt() {
	//log( "WARNING: operation 'sqrt' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::log2() {
	//log( "WARNING: operation 'log2' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::exp2() {
	//log( "WARNING: operation 'exp2' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::exp() {
	//log( "WARNING: operation 'exp' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::sin() {
	//log( "WARNING: operation 'sin' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::cos() {
	//log( "WARNING: operation 'cos' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::tan() {
	//log( "WARNING: operation 'tan' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::asin() {
	//log( "WARNING: operation 'asin' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::acos() {
	//log( "WARNING: operation 'acos' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::atan() {
	//log( "WARNING: operation 'atan' not permitted on class UnsignedCharValue!" );
	return *this;
}

Value& UnsignedCharValue::abs() {
	//log( "WARNING: operation 'abs' not permitted on class UnsignedCharValue!" );
	return *this;
}



// string compare
bool UnsignedCharValue::operator<( const Value& v ) const {
	//log( "WARNING: operator '<' not permitted on class UnsignedCharValue!" );
	return 0;
}

bool UnsignedCharValue::operator>( const Value& v ) const {
	//log( "WARNING: operator '>' not permitted on class UnsignedCharValue!" );
	return 0;
}

bool UnsignedCharValue::operator==( const Value& v ) const {
	//log( "WARNING: operator '==' not permitted on class UnsignedCharValue!" );
	return 0;
}

bool UnsignedCharValue::operator!=( const Value& v ) const {
	//log( "WARNING: operator '!=' not permitted on class UnsignedCharValue!" );
	return 0;
}

bool UnsignedCharValue::operator<=( const Value& v ) const {
	//log( "WARNING: operator '>=' not permitted on class UnsignedCharValue!" );
	return 0;
}

bool UnsignedCharValue::operator>=( const Value& v ) const {
	//log( "WARNING: operator '>=' not permitted on class UnsignedCharValue!" );
	return 0;
}



