case class CartesianCoordinates(x:Double, y:Double)

val findNorthEasternCoodinates = (c: List[CartesianCoordinates]) => {
  c.filter((elem => elem.x > 0 && elem.y > 0))
}

findNorthEasternCoodinates(List(CartesianCoordinates(1, 2),
  CartesianCoordinates(-2, 4),
  CartesianCoordinates(5, 6)))