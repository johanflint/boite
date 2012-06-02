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

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning

@RunWith(classOf[JUnitRunner])
class EqualsTest extends FlatSpec with ShouldMatchers {
  "equals and hashCode" should "be correct in Box" in {
    EqualsVerifier.forClass(classOf[Box[_]])
      .usingGetClass
      .suppress(Warning.IDENTICAL_COPY)
      .verify
  }

  it should "be correct in Full" in {
    EqualsVerifier.forClass(classOf[Full[_]])
      .withRedefinedSuperclass
      .usingGetClass
      .suppress(Warning.NULL_FIELDS)
      .verify
  }

  it should "be correct with respect to symmetry for an edge case in Full" in {
    val foo = "foo"
    val boxedFoo = Full(foo)

    (boxedFoo == foo) should equal (foo == boxedFoo)
  }

  it should "be correct in Empty" in {
    EqualsVerifier.forClass(classOf[BoiteVide])
      .withRedefinedSuperclass
      .usingGetClass
      .suppress(Warning.IDENTICAL_COPY)
      .verify
  }

  it should "be correct in Failure" in {
    EqualsVerifier.forClass(classOf[Failure])
      .withRedefinedSuperclass
      .suppress(Warning.STRICT_INHERITANCE, Warning.NULL_FIELDS)
      .verify
  }
}