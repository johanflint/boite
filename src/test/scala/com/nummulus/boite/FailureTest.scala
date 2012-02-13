package com.nummulus.boite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import java.io.FileNotFoundException

@RunWith(classOf[JUnitRunner])
class FailureTest extends FlatSpec with ShouldMatchers  {
  val Message = "Exception thrown"
  val Throwable = new Exception
  val FileNotFoundException = new FileNotFoundException(Message)
  
  val failure = new Failure(Message, Full(Throwable), null)
  
  "Failure" should "be empty" in {
    failure.isEmpty should equal (true)
  }
  
  "Failure" should "not be defined" in {
    failure.isDefined should equal (false)
  }
  
  "Open or" should "return the default value on failure" in {
    failure.openOr(404) should equal (404)
  }
  
  "Two failures with the same message and exception" should "be equal" in {
    failure == new Failure(Message, Full(Throwable), null) should be (true)

    Failure(Message) == Failure(Message) should be (true)
  }
  
  "Two failures with the same message and different exceptions" should "not be equal" in {
    failure == new Failure(Message, Full(FileNotFoundException), null) should be (false)
    
    Failure(Message) == Failure(Message + "!") should be (false)
  }
  
  "Failure with a throwable" should "copy the message correctly" in {
    Failure(FileNotFoundException).message == Message should be (true)
  }
}