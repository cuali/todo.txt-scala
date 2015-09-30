package cua.li.ti.todo

case class Task(
    priority: Option[String] = None,
    created: Option[String] = None,
    completed: Option[String] = None,
    contexts: Set[Context] = Set.empty,
    projects: Set[Project] = Set.empty,
    description: Option[String] = None
) {
  def before(that: Task): Boolean = {
    this.completed.getOrElse("z") > that.completed.getOrElse("z")   || // if this has been completed after that or this is not completed and that is, otherwise
    this.priority.getOrElse("zzz") < that.priority.getOrElse("zzz") || // if this priority is before that priority or this has no priority and that has, otherwise
    this.created.getOrElse("z") < that.created.getOrElse(" ")       || // if this has been created before that or that has no creation date and this has, otherwise
    this.description.getOrElse("") < that.description.getOrElse("")    // if this description is ordered before that description or this has no description
  }
}
case class Context(name: String)
case class Project(name: String)
