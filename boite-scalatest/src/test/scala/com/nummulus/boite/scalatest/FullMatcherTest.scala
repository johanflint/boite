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

@RunWith(classOf[JUnitRunner])
class FullMatcherTest extends BoxMatcherTestTrait {
  "apply" should { 
    "match a full" in {
      FullWithFoo should be a full
    }
    
    "not match non-failures" in {
      Empty should not be a (full)
      FailureWithException should not be a (full)
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("not a full") {
        Empty should be a full
      }
    }
  }
  
  "containing" should {
    "correctly match an object" in {
      FullWithFoo should be a (full containing "foo")
    }
    
    "not match a full with another object" in {
      FullWithFoo should not be a (full containing "another object")
    }
    
    "not match a full with an object of another class" in {
      FullWithFoo should not be a (full containing 0)
    }
    
    "not match a non-failure" in {
      Empty should not be a (full containing "foo")
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("full containing \"foo\"") {
        Empty should be a (full containing "foo")
      }
    }
  }
}