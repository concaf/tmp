val numbers = List(1,2,3,4)

def double(elem: Int) = {
  elem * elem
}

numbers.map(double)

// OR

numbers.map((elem: Int) => elem * elem)
