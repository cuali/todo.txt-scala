package cua.li.ti.todo

trait TodoSeq {
  def head: Task
  def tail: TodoSeq
  def isEmpty: Boolean
  def foreach(f: Task => Unit): Unit =
    if (!isEmpty) { // TODO remove this ugly test from here !!!
      f(head)
      tail.foreach(f)
    }
}

object TodoNil extends TodoSeq {
  def head = throw new java.util.NoSuchElementException("head of EmptySeq")
  def tail = throw new java.util.NoSuchElementException("tail of EmptySeq")
  def isEmpty = true
}

class TodoCons(val head: Task, val tail: TodoSeq) extends TodoSeq {
  def isEmpty = false
}
