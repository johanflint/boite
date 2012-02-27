package com.nummulus.boite

import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import java.io.FileNotFoundException
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class FailureTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  val Message = "Exception thrown"
  val Throwable = new Exception
  val FileNotFoundException = new FileNotFoundException(Message)
  
  val failure = new Failure(Message, Throwable)
  
  "Failure" should "be empty" in {
    failure.isEmpty should equal (true)
  }
  
  "Failure" should "not be defined" in {
    failure.isDefined should equal (false)
  }
  
  "getOrElse" should "return the default value on failure" in {
    failure.getOrElse(404) should equal (404)
  }
  
  "Failure with a throwable" should "copy the message correctly" in {
    Failure(FileNotFoundException).message == Message should be (true)
  }
  
  "map" should "return the same instance" in {
    failure.map(s => s) should equal (failure)
  }
  
  "flatMap" should "return the same instance" in {
    failure.flatMap(s => s) should equal (failure)
  }
  
  "foreach" should "not call anything" in {
    val test = mock[DummyTrait]
    failure.foreach(s => test.calculate(""))
    
    verify (test, never) calculate("")
  }
  
  "toList" should "return an empty list" in {
    Empty.toList should equal (List())
  }
  
  "Failure.apply(message, throwable)" should "be equal to calling new with the same arguments" in {
    Failure(Message, Throwable) should equal (failure)
  }
}