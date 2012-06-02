Boite
=====

Boite is a scala utility class that will help you eliminate NullPointerExceptions. It is an alternative to Scala's Option and to Lift's Box.

It defines a class `Box[T]` that can have three values: Empty, Full, or Failure. Also, it contains special matchers for use in unit tests written with ScalaTest.



How to use
----------

Add a dependency to `boite`. The following snippet of code show a typical usage of Boite:

    val user: Box[User] = Box.wrap {
      SecurityManager.findUser("jacques")
    }

    user match {
      case Full(u) => "Welcome, " + u.name + "!"
      case Empty   => "User not found"
      case Failure(_: IOException) => 
        "Could not connect to authentication server :("
    }

For more usage examples, see [Boite's test cases](https://github.com/nummulus/boite/tree/master/boite/src/test/scala/com/nummulus/boite).

If you use ScalaTest, you can add a test-scope dependency to `boite-scalatest`. The following snippet shows the matchers that you can use, and a number of other ways Boite can be used:

    class BoxTest extends FlatSpec with ShouldMatchers {
      behavior of "A Box"

      it should "be full" in {
        val b = Full("hello")

        b.getOrElse("nothing") should be ("hello")
        b should be a (Full containing "hello")
      }

      it should "be empty" in {
        val b = Empty

        b.getOrElse("nothing") should be ("nothing")
        b should be (empty)
      }

      it should "be a failure" in {
        val b = Box.wrap { "abc".toInt }

        b.getOrElse("nothing") should be ("nothing")
        b should be a (failure containing classOf[NumberFormatException] saying ("For input string:, "abc"))
      }
    }

For more ScalaTest examples, see [Boite's ScalaTest test cases](https://github.com/nummulus/boite/tree/master/boite-scalatest/src/test/scala/com/nummulus/boite/scalatest).



Rationale
---------

We decided to write Boite because none of the three previously existing approaches appealed to us.

* null
  Java's null has two main disadvantages. First, it's not always clear when a method can return null, and the compiler doesn't force you to check it, which results in those pesky NullPointerExceptions. Second, it's ambiguous. Null can either be a valid value, or it can be a signal that something went wrong. You never know for sure. 

* Scala's Option
  [Scala's Option](http://www.scala-lang.org/api/current/scala/Option.html) addresses these issues, but it doesn't handle the case when something goes wrong. You have to catch exceptions, which is easy to forget.

* Box
  [Lift's Box](http://www.assembla.com/spaces/liftweb/wiki/Box) addresses this issue, but brings in a number of it's own. First, it's a bit bloated, sporting many features that seem to be unnecesary, many of which have symbolic names that are hard to read. Also, its equals method is [broken](https://github.com/lift/framework/issues/1234). Most importantly, though, it requires you to depend on Lift, which is fine if you're building a Lift application, but very inconvenient in just about all other cases.

Boite provides the Box type, which addresses all these issues. It's lean and mean, it contains only the necessary features, and it's fully type-safe.



Naming
------

The library is called Boite, which is French for box. However, the class is still called `Box`, in order to keep your Scala code English.



Forking
-------

If you decide to fork Boite, be aware that because of a bug in Eclipse 3.7, you should not do so in a directory called `boite`. Boite is divided into two subprojects: boite proper, and the ScalaTest matchers. The former also lives in a directory called `boite`, and when Eclipse tries to import these projects, a name clash occurs that breaks your workspace.

Therefore, when you are using Eclipse and you want to clone the repository, we recommend that you do so in a directory called `boite-workspace` or something similar.



Acknowledgements
----------------

We would like to thank the [Lift](http://liftweb.net/) team for coming up with Box. Without it, there would have been no Boite.



Legal talk
----------

Copyright 2012 Nummulus

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

  <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
