package o1.hiddenpics

/**
 * @author Joll4
 */
class Test {
  def esimerkkiA(raja: Int) = {
  var luku = 1
  var nelio = 1
  do {
    println(nelio)
    luku += 1
    nelio = luku * luku
  } while (nelio <= raja)
}
  def esimerkkiB(raja: Int) = {
  var luku = 1
  var nelio = 1
  while (nelio <= raja) {
    println(nelio)
    luku += 1
    nelio = luku * luku
  }
}
}