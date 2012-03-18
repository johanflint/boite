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
      aFull should be a full
    }
    
    "not match non-failures" in {
      anEmpty should not be a (full)
      aFailure should not be a (full)
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("not a full") {
        anEmpty should be a full
      }
    }
  }
  
  "containing" should {
    "correctly match an object" in {
      aFull should be a (full containing "foo")
    }
    
    "not match a full with another object" in {
      aFull should not be a (full containing "another object")
    }
    
    "not match a full with an object of another class" in {
      aFull should not be a (full containing 0)
    }
    
    "not match a non-failure" in {
      anEmpty should not be a (full containing "foo")
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("full containing \"foo\"") {
        anEmpty should be a (full containing "foo")
      }
    }
  }
}