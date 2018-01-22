def ownMap[A, B](fn: A => B, lst: List[A]): List[B] = {
  var bList = List[B]()
  for(item <- lst) {
    bList = bList :+ fn(item)
  }
  bList
}

ownMap(c => {
  if ( c == "India") "Delhi"
  else if (c == "UK") "London"
}, List("India", "Bhutan", "UK"))