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
class FailMatcherTest extends BoxMatcherTestTrait {
  "apply" should {
    "match a failure" in {
      FailureWithException should be a failure
    }
    
    "not match non-failures" in {
      FullWithFoo should not be a (failure)
      Empty should not be a (failure)
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("not a failure") {
        Empty should be a failure
      }
    }
  }
  
  "saying" should {
    "correctly match a message" in {
      FailureWithException should be a (failure saying Message)
    }
    
    "not match a failure with another message" in {
      FailureWithException should not be a (failure saying "something else")
    }
    
    "not match a non-failure" in {
      Empty  should not be a (failure saying Message)
    }
    
    "match a substring" in {
      FailureWithException should be a (failure saying "message")
    }
    
    "match several substrings" in {
      FailureWithException should be a (failure saying ("this", "exception", "message"))
    }
    
    "not match if one substring doesn't match" in {
      FailureWithException should not be a (failure saying ("this", "exception", "does not match"))
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("failure saying \"this\", \"exception\", \"message\"") {
        Empty should be a (failure saying ("this", "exception", "message"))
      }
    }
  }
  
  "containing" should {
    "correctly match an exception" in {
      FailureWithException should be a (failure containing classOf[IllegalStateException])
    }
    
    "not match a failure with another exception" in {
      FailureWithException should not be a (failure containing classOf[NullPointerException])
    }
    
    "not match a non-failure" in {
      Empty should not be a (failure containing classOf[IllegalStateException])
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("failure containing IllegalStateException") {
        Empty should be a (failure containing classOf[IllegalStateException])
      }
    }
  }
  
  "containing/saying" should {
    "correctly match an exception and a substring" in {
      FailureWithException should be a (failure containing classOf[IllegalStateException] saying Message)
    }
    
    "correctly match an exception and several substrings" in {
      FailureWithException should be a (failure containing classOf[IllegalStateException] saying ("this", "exception", "message"))
    }
    
    "not match a failure with another exception" in {
      FailureWithException should not be a (failure containing classOf[NullPointerException] saying Message)
    }
    
    "not match a failure with another message" in {
      FailureWithException should not be a (failure containing classOf[IllegalStateException] saying "something else")
    }
    
    "not match if one substring does not match" in {
      FailureWithException should not be a (failure containing classOf[IllegalStateException] saying ("this", "exception", "does not match"))
    }
    
    "not match a non-failure" in {
      Empty should not be a (failure containing classOf[IllegalStateException] saying Message)
    }
    
    "give an appropriate error message if the exception is incorrect" in {
      itShouldFailSaying("failure", "containing NullPointerException") {
        FailureWithException should be a (failure containing classOf[NullPointerException] saying Message)
      }
    }
    
    "give an appropriate error message if the message is incorrect" in {
      itShouldFailSaying("failure", "saying \"something else\"") {
        FailureWithException should be a (failure containing classOf[IllegalStateException] saying "something else")
      }
    }
    
    "give an appropriate error message if both are incorrect" in {
      itShouldFailSaying("failure", "containing NullPointerException", "saying \"something else\"") {
        FailureWithException should be a (failure containing classOf[NullPointerException] saying "something else")
      }
    }
  }
}
