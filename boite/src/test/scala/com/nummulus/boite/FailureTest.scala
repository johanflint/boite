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
  
  val FailureWithThrowable = Failure(Throwable)
  
  behavior of "Failure"
  
  it should "be empty" in {
    FailureWithThrowable.isEmpty should be (true)
  }
  
  it should "not be defined" in {
    FailureWithThrowable.isDefined should be (false)
  }
  
  "get" should "throw a NoSuchElementException" in {
    val e = intercept[NoSuchElementException] {
      FailureWithThrowable.get
    }
    e.getMessage should be ("Box does not contain a value")
  }
  
  "getOrElse" should "return the default value on Failure" in {
    FailureWithThrowable.getOrElse(404) should be (404)
  }
  
  "map" should "return the same instance" in {
    FailureWithThrowable map { s => s } should be (FailureWithThrowable)
  }
  
  "flatMap" should "return the same instance" in {
    FailureWithThrowable flatMap { s => s } should be (FailureWithThrowable)
  }
  
  "foreach" should "not call anything" in {
    val test = mock[DummyTrait]
    FailureWithThrowable foreach { s => test.calculate("") }
    
    verify (test, never) calculate("")
  }
  
  "toList" should "return an empty list" in {
    Empty.toList should be (List())
  }
}