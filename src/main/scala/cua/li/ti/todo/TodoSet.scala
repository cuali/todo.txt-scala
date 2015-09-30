package cua.li.ti.todo

//// this code was inspired by assignment of week 3 in the course
//// Functional Programming Principles in Scala
//// by Martin Odersky
//// on coursera https://class.coursera.org/progfun-005

/**
 * This represents a set of objects of type `Task` in the form of a binary search
 * tree. Every branch in the tree has two children (two `TodoSet`s). There is an
 * invariant which always holds: for every branch `b`, all elements in the left
 * subtree are smaller than the Task at `b`. The elements in the right subtree are
 * larger.
 *
 * Note that the above structure requires us to be able to compare two Tasks (we
 * need to be able to say which of two Tasks is larger, or if they are equal). In
 * this implementation, the equality / order of Tasks is based on the Task's
 * description (see `def include`). Hence, a `TodoSet` could not contain two Tasks
 * with the same description.
 *
 *
 * The advantage of representing sets as binary search trees is that the elements
 * of the set can be found quickly. If you want to learn more you can take a look
 * at the Wikipedia page [1], but this is not necessary.
 *
 * [1] http://en.wikipedia.org/wiki/Binary_search_tree
 */
abstract class TodoSet {

  /**
   * This method takes a predicate and returns a subset of all the elements
   * in the original set for which the predicate is true.
   *
   * Question: Can we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def filter(p: Task => Boolean): TodoSet = ???

  /**
   * This is a helper method for `filter` that propagates the accumulated Tasks.
   */
  protected def filterAcc(p: Task => Boolean, acc: TodoSet): TodoSet

  /**
   * Returns a new `TodoSet` that is the union of `TodoSet`s `this` and `that`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def union(that: TodoSet): TodoSet = ???

  /**
   * This method takes a predicate and returns a new `TodoSeq` with all the elements
   * of the original set, sorted according to the order defined by the `lt` predicate.
   * In other words, the head of the resulting sequence should have the lowest value
   * according to the `lt` predicate.
   * 
   * The predicate must return `true` if its first argument has a lower value than
   * its second argument.
   *
   * Question: Can we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def sortWith(lt: (Task, Task) => Boolean): TodoSeq = ???

  //
  // The following methods are implemented in the subclasses
  //

  /**
   * Tests if `Task` exists in this `TodoSet`.
   */
  def contains(tsk: Task): Boolean

  /**
   * Returns a new `TodoSet` which contains all elements of this set, and the
   * the new element `Task` in case it does not already exist in this set.
   *
   * If `this.contains(Task)`, the current set is returned.
   */
  def include(tsk: Task): TodoSet

  /**
   * Returns a new `TodoSet` which excludes `Task`.
   */
  def remove(tsk: Task): TodoSet

  /**
   * This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Task => Unit): Unit
}

class Empty extends TodoSet {
  override def filterAcc(p: Task => Boolean, acc: TodoSet): TodoSet = ???

  /**
   * The following methods are already implemented
   */

  def contains(tsk: Task): Boolean = false

  def include(tsk: Task): TodoSet = new Holding(tsk, new Empty, new Empty)

  def remove(tsk: Task): TodoSet = this

  def foreach(f: Task => Unit): Unit = ()
}

class Holding(elem: Task, left: TodoSet, right: TodoSet) extends TodoSet {

  def filterAcc(p: Task => Boolean, acc: TodoSet): TodoSet = ???

  def contains(tsk: Task): Boolean = ???

  def include(tsk: Task): TodoSet = ???

  /**
   * The following methods are already implemented
   */

  def remove(tsk: Task): TodoSet =
    if (tsk before elem) new Holding(elem, left.remove(tsk), right)
    else if (elem before tsk) new Holding(elem, left, right.remove(tsk))
    else left.union(right)

  def foreach(f: Task => Unit): Unit = {
    f(elem)
    left.foreach(f)
    right.foreach(f)
  }
}
