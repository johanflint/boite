/*
 * Copyright 2012 Nummulus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nummulus.boite

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoxTest extends FlatSpec with ShouldMatchers {
  val GilbertBecaud = "Gilbert Bécaud"
  val NotSupported = "not supported"
  val Exception = new UnsupportedOperationException(NotSupported)
  val Error = new AssertionError
  
  "wrap" should "return Empty when it is passed null" in {
    Box wrap { null } should be (Empty)
  }
  
  it should "return Full(value) when it is passed a value" in {
    Box wrap { GilbertBecaud } should equal (Full(GilbertBecaud))
  }
  
  it should "return Failure when an exception is thrown while evaluating the expression passed to it" in {
    Box wrap { throw Exception } should equal (Failure(Exception))
  }
  
  it should "let Errors pass through" in {
    intercept[AssertionError] {
      Box wrap { throw Error }
    }
  }
  
  "apply" should "accept an empty Option and return Empty" in {
    Box(None) should equal (Empty)
  }
  
  it should "accept a full Option and return Full" in {
    Box(Some(GilbertBecaud)) should equal (Full(GilbertBecaud))
  }
}