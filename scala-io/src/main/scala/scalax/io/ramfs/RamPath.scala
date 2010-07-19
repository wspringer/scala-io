/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2009-2010, Jesse Eichar          **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scalax.io.ramfs

import scala.resource.ManagedResource
import scalax.io.{
  FileSystem, Path, FileOps,Codec,PathMatcher,DirectoryStream, LinkOption
}
import scalax.io.attributes.FileAttribute

import Path.AccessModes._
import java.net.{
  URL,URI
}

class RamPath(relativeTo:String, val path:String, override val fileSystem:RamFileSystem) extends Path(fileSystem) {
    lazy val toAbsolute: Path = fileSystem.apply("",relativeTo + fileSystem.separator + path)
    lazy val toURI: URI = fileSystem.uri(this)
    def \(child: String): RamPath = fileSystem.apply(this.toAbsolute.path,child)
    lazy val name: String = segments.last
    lazy val normalize: RamPath = null //TODO
    lazy val parent: Option[RamPath] = {
      val path = toAbsolute.path.dropRight(toAbsolute.name.length)
      if(path.length == 0 || path == fileSystem.separator.toString) {
        None
      } else {
        Some(fileSystem.apply("",path))
      }
    }
    def checkAccess(modes: AccessMode*): Boolean = false //TODO
    def canWrite = false // TODO
    def canRead = false // TODO
    def canExecute = false //TODO
    def exists = false //TODO
    def isFile = false //TODO
    def isDirectory = false //TODO
    def isAbsolute = false //TODO
    def isHidden = false //TODO
    def lastModified = 0 //TODO
    def lastModified_=(time: Long) = 0 //TODO
    def size = 0 //TODO
    def access_=(accessModes:Iterable[AccessMode]) = () // TODO
    def access : Set[AccessMode] = null
    def createFile(createParents: Boolean = true, failIfExists: Boolean = true,
                   accessModes:Iterable[AccessMode]=List(Read,Write), 
                   attributes:Iterable[FileAttribute[_]]=Nil): Path = null //TODO
    def createDirectory(createParents: Boolean = true, failIfExists: Boolean = true,
                        accessModes:Iterable[AccessMode]=List(Read,Write), 
                        attributes:Iterable[FileAttribute[_]]=Nil) = null //TODO
    def delete(force:Boolean): Path = this //TODO
    def copyTo(target: Path, 
               createParents : Boolean = true,
               copyAttributes:Boolean=true, 
               replaceExisting:Boolean=false): Path = null //TODO
     protected def moveFile(target: Path, atomicMove:Boolean): Unit = null // TODO
     protected def moveDirectory(target: Path, depth:Int, atomicMove:Boolean): Unit = null // TODO
    def execute(args:String*)(implicit configuration:ProcessBuilder=>Unit = p =>()):Option[scalax.io.Process] = null // TODO

    override def toString() = "RamPath(%s)".format(path)
    override def equals(other: Any) = other match {
      case x: Path  => path == x.path
      case _        => false
    }  
    override def hashCode() = path.hashCode()

    def descendants(filter:Path => Boolean, depth:Int, options:Traversable[LinkOption]):DirectoryStream[Path] = null //TODO

    def ops:FileOps = new RamFileOps(this)
}