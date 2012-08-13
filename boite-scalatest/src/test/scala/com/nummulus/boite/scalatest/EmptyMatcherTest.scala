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

package com.nummulus
package boite
package scalatest

import BoiteMatchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec

@RunWith(classOf[JUnitRunner])
class EmptyMatcherTest extends FlatSpec with BoxMatcherTestTrait {
  behavior of "apply"
  
  it should "match an empty" in {
    Empty should be (empty)
  }
  
  it should "not match non-empties" in {
    FullWithFoo should not be (empty)
    FailureWithException should not be (empty)
  }
  
  it should "give an appropriate error message" in {
    itShouldFailSaying("Full(foo) was not empty") {
      FullWithFoo should be (empty)
    }
    
    itShouldFailSaying("Empty was empty") {
      Empty should not be (empty)
    }
  }
}