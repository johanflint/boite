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
      itShouldFailSaying("not a failure") {
        anEmpty should be a failure
      }
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
      itShouldFailSaying("failure saying \"this\", \"exception\", \"message\"") {
        anEmpty should be a (failure saying ("this", "exception", "message"))
      }
    }
  }
  
  "containing" should {
    "correctly match an exception" in {
      aFailure should be a (failure containing classOf[IllegalStateException])
    }
    
    "not match a failure with another exception" in {
      aFailure should not be a (failure containing classOf[NullPointerException])
    }
    
    "not match a non-failure" in {
      anEmpty should not be a (failure containing classOf[IllegalStateException])
    }
    
    "give an appropriate error message" in {
      itShouldFailSaying("failure containing IllegalStateException") {
        anEmpty should be a (failure containing classOf[IllegalStateException])
      }
    }
  }
  
  "containing/saying" should {
    "correctly match an exception and a substring" in {
      aFailure should be a (failure containing classOf[IllegalStateException] saying aMessage)
    }
    
    "correctly match an exception and several substrings" in {
      aFailure should be a (failure containing classOf[IllegalStateException] saying ("this", "exception", "message"))
    }
    
    "not match a failure with another exception" in {
      aFailure should not be a (failure containing classOf[NullPointerException] saying aMessage)
    }
    
    "not match a failure with another message" in {
      aFailure should not be a (failure containing classOf[IllegalStateException] saying "something else")
    }
    
    "not match if one substring does not match" in {
      aFailure should not be a (failure containing classOf[IllegalStateException] saying ("this", "exception", "does not match"))
    }
    
    "not match a non-failure" in {
      anEmpty should not be a (failure containing classOf[IllegalStateException] saying aMessage)
    }
    
    "give an appropriate error message if the exception is incorrect" in {
      itShouldFailSaying("failure", "containing NullPointerException") {
        aFailure should be a (failure containing classOf[NullPointerException] saying aMessage)
      }
    }
    
    "give an appropriate error message if the message is incorrect" in {
      itShouldFailSaying("failure", "saying \"something else\"") {
        aFailure should be a (failure containing classOf[IllegalStateException] saying "something else")
      }
    }
    
    "give an appropriate error message if both are incorrect" in {
      itShouldFailSaying("failure", "containing NullPointerException", "saying \"something else\"") {
        aFailure should be a (failure containing classOf[NullPointerException] saying "something else")
      }
    }
  }
  
  def itShouldFailSaying(substrings: String*)(block: => Unit) {
    val msg = intercept[TestFailedException](block).getMessage
    substrings foreach { msg should include (_) }
  }
}
