package cua.li.ti.todo

import scala.io.StdIn
import scala.util.{ Failure, Success }
import org.parboiled2.{ ErrorFormatter, ParseError }

object TodoList extends App {
  val tasks = interpret(StdIn.readLine())
  // ??? pretty print the sequence of tasks
  tasks.foreach(println(_)) // the result will not be human-friendly

  private def interpret(input: String, tasks: Seq[Task] = Seq.empty): Seq[Task] = {
    input match {
      case "" =>
        tasks
      case line =>
        val parser = TodoParser(line)
        parser.task.run() match {
          case Failure(error: ParseError) =>
            println(parser.formatError(error, new ErrorFormatter(showTraces = true)))
            tasks
          case Failure(exception) =>
            println(exception)
            tasks
          case Success(task) =>
            interpret(StdIn.readLine(), tasks :+ task)
        }
    }
  }
}
