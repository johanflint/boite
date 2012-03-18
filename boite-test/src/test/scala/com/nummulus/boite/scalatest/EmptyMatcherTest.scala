package com.nummulus
package boite
package scalatest

import BoiteMatchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EmptyMatcherTest extends BoxMatcherTestTrait {
  "apply" should {
    "match an empty" in {
      anEmpty should be (empty)
    }
    
    "not match non-empties" in {
      aFull should not be (empty)
      aFailure should not be (empty)
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("Full(foo) was not empty") {
        aFull should be (empty)
      }
      
      itShouldFailSaying("Empty was empty") {
        anEmpty should not be (empty)
      }
    }
  }
  
}