import java.awt.Color
import java.awt.Rectangle
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main(args: Array<String>) {
    val inputPath = args[0]
    val outputPath = args[1]
    val whiteSquareCount = 12

    File(inputPath).walkTopDown().forEach {
        if (it.isFile) {
            var image = ImageIO.read(it)

            var newMeasure = if (image.width < image.height) image.width else image.height
            newMeasure = (newMeasure / whiteSquareCount) * whiteSquareCount
            val x = (image.width - newMeasure) / 2
            val y = (image.height - newMeasure) / 2

            image = image.getSubimage(x, y, newMeasure, newMeasure)

            val whiteSquareSize = newMeasure / whiteSquareCount

            val g2d = image.createGraphics()

            val pointSet = generatePoint()

            for (point in pointSet) {
                val x = point.x * whiteSquareSize
                val y = point.y * whiteSquareSize
                g2d.color = Color.WHITE
                g2d.fill(Rectangle(x, y, whiteSquareSize, whiteSquareSize))
            }

            g2d.dispose()

            val imgResult = File("${outputPath}/pixel-${it.name}")
            ImageIO.write(image, "jpeg", imgResult)
        }
    }
}

data class Point(val x: Int, val y: Int)

fun generatePoint() : Set<Point> {
    val pointSet = mutableSetOf<Point>()

    while (pointSet.size != 5) {
        val i = Random.nextInt(0, 11)
        if (i < 2 || i > 9) {
            val j = Random.nextInt(0, 11)
            pointSet.add(Point(i, j))
        } else if (i > 1 || i < 10) {
            val j = arrayOf(0, 1, 10, 11).random()
            pointSet.add(Point(i, j))
        }
    }

    return pointSet
}