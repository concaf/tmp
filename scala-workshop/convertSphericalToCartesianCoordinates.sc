case class SphericalCoordinates(r:Double, theta:Double, phi:Double)
case class CartesianCoordinates(x:Double, y:Double, z:Double)

val sc1 = SphericalCoordinates(5, 60, 30)
val sc2 = SphericalCoordinates(6, 65, 35)

val convert = (input: Set[SphericalCoordinates]) => {
  input.map(sc => {
    // x = r*sin(phi)*cos(theta)
    val x = sc.r * math.sin(sc.phi) * math.cos(sc.theta)
    // y = r*sin(phi)*sin(theta)
    val y = sc.r * math.sin(sc.phi) * math.sin(sc.theta)
    // z = r * cos(phi)
    val z = sc.r * math.cos(sc.phi)
    CartesianCoordinates(x, y, z)
  })
}

convert(Set(sc1, sc2))