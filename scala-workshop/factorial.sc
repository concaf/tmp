def findFactorial (input: Int): Int = input match {
    case 0 => 1
    case _ => input * findFactorial(input - 1)
  }

findFactorial(10)