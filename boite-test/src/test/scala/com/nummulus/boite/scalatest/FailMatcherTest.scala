package com.nummulus
package boite
package scalatest

import BoiteMatchers._
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.TestFailedException
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class FailMatcherTest extends WordSpec with ShouldMatchers {
  val aMessage    = "this is the exception message"
  val anException = new IllegalStateException(aMessage)
  
  val aFull    = Full("foo")
  val anEmpty  = Empty
  val aFailure = Failure(anException)

  "apply" should {
    "match a failure" in {
      aFailure should be a failure
    }
    
    "not match non-failures" in {
      aFull should not be a (failure)
      anEmpty should not be a (failure)
    }
    
    "give an appropriate error message" in {
      val thrown = intercept[TestFailedException] {
        anEmpty should be a failure
      }
      thrown.getMessage should include ("not a failure")
    }
  }
  
  "saying" should {
    "correctly match a message" in {
      aFailure should be a (failure saying aMessage)
    }
    
    "not match a failure with another message" in {
      aFailure should not be a (failure saying "something else")
    }
    
    "not match a non-failure" in {
      anEmpty  should not be a (failure saying aMessage)
    }
    
    "match a substring" in {
      aFailure should be a (failure saying "message")
    }
    
    "match several substrings" in {
      aFailure should be a (failure saying ("this", "exception", "message"))
    }
    
    "not match if one substring doesn't match" in {
      aFailure should not be a (failure saying ("this", "exception", "does not match"))
    }
    
    "give an appropriate error message" in {
      val thrown = intercept[TestFailedException] {
        anEmpty should be a (failure saying ("this", "exception", "message"))
      }
      thrown.getMessage should include ("failure saying \"this\", \"exception\", \"message\"")
    }
  }
}
