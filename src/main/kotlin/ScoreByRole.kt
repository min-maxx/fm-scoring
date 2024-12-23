package fr.maxx4web

import java.nio.file.Files
import java.nio.file.Path
import java.text.DecimalFormat
import kotlin.io.path.name
import kotlin.math.roundToInt

fun main() {
//    val root = "src/main/resources"
    val scrRoot = "/Users/maxime_pelletier/Library/Application Support/Sports Interactive/Football Manager 2024/_exports"
    val targetRoot = "src/main/resources"

    val targetDirectory = Path.of(targetRoot).toFile()
    targetDirectory.listFiles().orEmpty().forEach { it.deleteRecursively() }
    Path.of(scrRoot).toFile().copyRecursively(targetDirectory)

    val scoresByPosition: List<Pair<String, List<Pair<String, Double>>>> = targetDirectory.listFiles().orEmpty()
        .filter { it.isDirectory }
        .filter { !it.isHidden }
        .sorted()
        .flatMap { dir ->
            dir.listFiles().orEmpty()
                .sorting()
                .filter { it.isFile && !it.isHidden }
                .map { score(it.toPath()) }
        }

    scoresByPosition.forEach { (position, players) ->
        println("Position: $position")
        println("--")
        players
            .filterNot { (player, score) ->  player.contains("Souahy") }
            .take(5).forEach { (player, score) ->
            val isBestScore = scoresByPosition.flatMap { it.second }
                .filter { it.first == player }
                .maxByOrNull { it.second }?.second == score
            val playerName = if (isBestScore) "$player (*)" else "$player (-)"
                .let {
                    val isFirstInOtherPosition = scoresByPosition.any { (pos, players) ->
                        pos != position && players.firstOrNull()?.first == player
                    }
                    if (isFirstInOtherPosition) "$it (1)" else it
                }
                println("$playerName: $score")
        }
        println("-------------------")

    }



//    targetDirectory.listFiles().orEmpty()
//        .filter { it.isDirectory }
//        .filter { !it.isHidden }
//        .sorted()
//        .forEach { dir ->
//            println("\n$## ${dir.name}")
//            dir.listFiles().orEmpty()
//                .sorting()
//                .filter { it.isFile && !it.isHidden }
//                .map { score(it.toPath(), top = 10) }
////                .map { score(it.toPath(), name = "Diamé") }
//                .sortedWith(compareBy { lines ->
//                    if (lines.size == 3) lines[2]
//                    else lines[0]
//                })
//                .filter { lines -> lines.size > 3 } // eviter les poste vide quand on filtre par nom
//                .flatten()
//                .forEach { println("_${it}_") }
//
//        }
}


private fun <T> Array<T>.sorting(): Array<T> {
    this.sort()
    return this
}


private fun score(path: Path, top: Int = 5, skip: Int = 0, name: String? = null): List<String> {
//    println("path = ${path}")
    val lineScorePair = score(path)

    val scoredHeader = ""
        .prepend("Nom", 25)
        .prepend("Score", 6)

    val scoredLines = lineScorePair.second
        .filter { (line, _) -> name?.let { line.contains(name) } ?: true }
        .filterIndexed { index, _ -> name?.let { true } ?: IntRange(skip, top - 1).contains(index) }
        .map { (line, score) -> line.prepend(score.on2digits(), 6) }

    return listOf("", "file = ${path.fileName}", scoredHeader) + scoredLines

}

private fun score(path: Path): Pair<String, List<Pair<String, Double>>> {
    val (header, lines) = cleanup(Files.readAllLines(path)).headTail()
//    println("path.name = ${path.name}")
//    println("header = ${header}")
    val keyCols = keyCols(header, path.name)
    if (keyCols.isEmpty()) {
        println("/!\\ missing keyCols for  ${path.fileName}")
    }
    val nameIndex: Int = header.split("|")
        .mapIndexed { index, col -> col to index }
        .first { it.first.contains("Nom") }
        .second
    val lineScorePair = lines.map { line -> player(line, nameIndex) to score(line, keyCols) }
        .sortedWith(compareByDescending({ it.second }))
    return path.name to lineScorePair
}

fun player(line: String, nameIndex: Int): String {
//    println("line = ${line}")
    return line.split("|")[nameIndex].trim()
}


val formatter = DecimalFormat("#.00")
private fun Double.on2digits(): String = formatter.format(this)

private fun List<String>.headTail() = first() to drop(1)

///GB
val GlSo: IntArray = intArrayOf(2, 5, 6, 7, 9, 12, 15, 16, 17, 19)
///DC
val DcsSt: IntArray = intArrayOf(1, 3, 4, 6, 7, 9, 10)
val DcDé: IntArray = intArrayOf(1, 2, 3, 8, 12, 13)
val DcCo: IntArray = intArrayOf(2, 3, 4, 6, 7, 9, 10)
val DerDé: IntArray = intArrayOf(1, 2, 4, 12, 16, 17)
val DerCo: IntArray = intArrayOf(2, 3, 4, 7, 13, 10, 11, 12, 14)
val DceSo: IntArray = intArrayOf(2, 3, 4, 5, 14, 19, 20, 22)
val DceDé: IntArray = intArrayOf(2, 3, 5, 12, 18, 19)
val LibDé: IntArray = intArrayOf(1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 16, 17)

///AL
val DlAt: IntArray = intArrayOf(1, 3, 5, 8, 11, 12)
val LofAt: IntArray = intArrayOf(1, 2, 5, 6, 11, 13, 14, 16, 19, 20)
val LocAt: IntArray = intArrayOf(1, 2, 6, 10, 11, 13, 14, 15, 19)
val LofDé: IntArray = intArrayOf(3, 5, 8, 11, 12, 13, 15, 19)
val LofSo: IntArray = intArrayOf(1, 2, 3, 5, 10, 12, 13, 15, 19)
val LiDé: IntArray = intArrayOf(2, 3, 6, 7, 9, 10)
val LiSo: IntArray = intArrayOf(2, 3, 5, 8, 11, 12)

///MD
val MDéDé: IntArray = intArrayOf(3, 5, 7, 8, 11)
val MDéSo: IntArray = intArrayOf(3, 5, 6, 8, 9)
val MréDé: IntArray = intArrayOf(2, 3, 4, 7, 8, 12)
val MréSo: IntArray = intArrayOf(3, 4, 5, 7, 8, 12)
val MjRDé: IntArray = intArrayOf(1, 3, 4, 6, 7, 9, 10)
val MjRSo: IntArray = intArrayOf(1, 2, 3, 4, 6, 9, 10)
val RegSo: IntArray = intArrayOf(3, 4, 5, 7, 8, 9, 10, 11, 12)
val MjlSo: IntArray = intArrayOf(3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 15, 19)
val VolSo: IntArray = intArrayOf(3, 4, 5, 9, 10, 11, 16, 17)
val VolAt: IntArray = intArrayOf(1, 2, 4, 5, 7, 9, 10, 11, 16, 17)
val AiDSo: IntArray = intArrayOf(1, 6, 11, 13, 14, 18)

///MC
val B2bSo: IntArray = intArrayOf(4, 5, 12, 13, 14, 19)
val MaSo: IntArray = intArrayOf(1, 2, 4, 7, 9)
val CarSo: IntArray = intArrayOf(1, 2, 4, 7, 9, 10, 14)
val MezSo: IntArray = intArrayOf(3, 5, 9, 10, 11, 13)
val MezAt: IntArray = intArrayOf(1, 4, 5, 8, 9, 11, 12, 14)

///MO
val NeufDemi: IntArray = intArrayOf(1, 2, 5, 6, 8, 10, 12)
val AtsAt: IntArray = intArrayOf(1, 3, 4, 5, 8, 9, 10, 12)
val MjaSo: IntArray = intArrayOf(2, 3, 4, 6, 7, 9, 10, 11)
val MjaAt: IntArray = intArrayOf(2, 3, 4, 6, 7, 9, 10, 11)
val MoSo: IntArray = intArrayOf(2, 3, 4, 5, 6, 8, 9, 10)
val MoAt: IntArray = intArrayOf(1, 3, 4, 5, 6, 7, 9, 10, 11)
val AilSo: IntArray = intArrayOf(1, 2, 4, 8, 9)
val AilAt: IntArray = intArrayOf(1, 2, 4, 10, 11)
val AiSo: IntArray = intArrayOf(1, 2, 4, 5, 12, 13)
val AiAt: IntArray = intArrayOf(1, 2, 4, 5, 14, 15)
val AtiSo: IntArray = intArrayOf(1, 2, 5, 6, 10, 13, 14)
val AtiAt: IntArray = intArrayOf(1, 2, 5, 6, 7, 9, 12, 13)
val RmdAt: IntArray = intArrayOf(1, 4, 5, 6, 8, 9, 11)

///AT
val AtaAt: IntArray = intArrayOf(1, 2, 4, 5, 8, 10, 11)
val F9So: IntArray = intArrayOf(1, 3, 4, 5, 7, 8, 10, 12, 13, 14)
val AtrSo: IntArray = intArrayOf(2, 3, 4, 7, 9, 10, 11)
val ApDé: IntArray = intArrayOf(2, 3, 4, 5, 6, 7, 10, 13, 14)
val ApSo: IntArray = intArrayOf(3, 4, 5, 6, 8, 9, 12, 15, 16)
val ApAt: IntArray = intArrayOf(3, 4, 5, 7, 8, 9, 12, 15, 16)
val AtcSo: IntArray = intArrayOf(1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 15, 16, 20)
val AtcAt: IntArray = intArrayOf(1, 2, 3, 6, 7, 8, 11, 14, 15, 16, 20)

private fun keyCols(header: String, fileName: String): IntArray {
    val keyCols = when {
        "Dét| Srf| Com| Pbl| Dég| Réf| TSP| Rel| 1c1| Pas| Ctr| Ant| Vis| Déc| Pla| Sgf| Ctn| Acc| Agi" in header -> GlSo
        "Têt | Mar | Tcl | Agr | Ant | Crg | Déc | Pla | Sgf | Ctn | Vit | Pui | Dét" in header -> DcDé
        "Têt  | Mar  | Tcl  | Ant  | Crg  | Déc  | Pla  | Sgf  | Ctn  | Vit  | Pui  | Dét" in header -> DcCo
        "Têt  | Mar  | Tcl  | Agr  | Ant  | Crg  | Pla  | Ctn  | Pui  | Dét" in header -> DcsSt
        "Têt| Mar| Pas| Tcl| Tec| Ctr| Agr| Ant| Crg| Vis| Déc| Pla| Sgf| Ctn| Vit| Pui| Dét" in header -> DerDé
        "Têt | Mar | Pas | Tcl | Tec | Ctr | Ant | Crg | Vis | Déc | Pla | Sgf | Ctn | Vit | Pui | Dét" in header -> DerCo
        "Cen| Drb| Têt| Mar| Pas| Tcl| Tec| Ctr| Agr| Ant| Crg| Déc| Apl| Pla| Vol| Sgf| Ctn| Agi| Vit| End| Pui| Dét" in header -> DceSo //TODO At
        "Drb| Têt| Mar| Pas| Tcl| Tec| Ctr| Agr| Ant| Crg| Déc| Pla| Vol| Sgf| Ctn| Agi| Vit| Pui| Dét" in header -> DceDé
        "Têt| Mar| Pas| Tcl| Tec| Ctr| Ant| Crg| Déc| Pla| Col| Sgf| Ctn| Vit| End| Pui| Dét" in header -> LibDé
        "Cen| Drb| Mar| Pas| Tcl| Tec| Ctr| Ant| Déc| Apl| Pla| Col| Vol| Ctn| Acc| Agi| Équ| Vit| End" in header -> if (fileName.contains("So")) LofSo else LofDé
        "Cen| Drb| Mar| Pas| Tcl| Tec| Ctr| Ant| Déc| Apl| Pla| Col| Vol| Ctn| Agi| Vit| End" in header -> DlAt
        "Cen| Drb| Mar| Pas| Tcl| Tec| Ctr| Ant| Déc| Ins| Apl| Pla| Col| Vol| Ctn| Acc| Agi| Équ| Vit| End" in header -> LofAt
        "Cen| Drb| Mar| Pas| Tcl| Tec| Ctr| Ant| Déc| Ins| Apl| Pla| Col| Vol| Acc| Agi| Équ| Vit| End" in header -> LocAt
        "Mar | Pas | Tcl | Tec | Ctr | Ant | Déc | Apl | Pla | Col | Vol | Sgf | Ctn | Acc | Agi | End" in header -> LiDé
        "Mar| Pas| Tcl| Tec| Ctr| Ant| Vis| Déc| Apl| Pla| Col| Vol| Sgf| Ctn| Acc| Agi| End" in header -> LiSo
        "Mar | Pas | Tcl | Agr | Ant | Déc | Pla | Col | Vol | Sgf | Ctn | End | Pui" in header -> MDéDé
        "Mar | Pas | Tcl | Ctr | Agr | Ant | Déc | Pla | Col | Vol | Sgf | Ctn | End | Pui" in header -> MDéSo
        "Mar | Tcl | Agr | Ant | Crg | Pla | Col | Vol | Ctn | Agi | Vit | End | Pui" in header -> MréDé
        "Mar | Pas | Tcl | Agr | Ant | Crg | Col | Vol | Ctn | Agi | Vit | End | Pui" in header -> MréSo
        "Pas  | Tcl  | Tec  | Ctr  | Ant  | Vis  | Déc  | Pla  | Col  | Sgf  | Équ" in header -> MjRDé
        "Pas  | Tec  | Ctr  | Ant  | Vis  | Déc  | Apl  | Pla  | Col  | Sgf  | Équ" in header -> MjRSo
        "Drb | Tir | Pas | Tec | Ctr | Ant | Vis | Déc | Ins | Apl | Col | Sgf | Équ" in header -> RegSo
        "Drb| Tir| Pas| Tec| Ctr| Ant| Vis| Déc| Apl| Pla| Col| Vol| Sgf| Ctn| Acc| Agi| Équ| Vit| End" in header -> MjlSo
        "Fin| Tir| Mar| Pas| Tcl| Ctr| Ant| Déc| Apl| Pla| Vol| Sgf| Ctn| Acc| Équ| Vit| End| Pui" in header -> if (fileName.contains("So")) VolSo else VolAt
        "Cen| Drb| Mar| Pas| Tcl| Tec| Ctr| Agr| Ant| Déc| Apl| Pla| Col| Vol| Sgf| Ctn| Acc| End" in header -> AiDSo
        "Drb| Fin| Tir| Pas| Tcl| Tec| Ctr| Agr| Ant| Déc| Apl| Pla| Col| Vol| Sgf| Acc| Équ| Vit| End| Pui" in header -> B2bSo
        "Pas | Tcl | Tec | Ctr | Ant | Vis | Déc | Apl | Col | Vol | Sgf | Ctn | End" in header -> MaSo
        "Pas | Tcl | Tec | Ctr | Ant | Vis | Déc | Apl | Pla | Col | Vol | Sgf | Ctn | End" in header -> CarSo
        "Drb | Tir | Pas | Tcl | Tec | Ctr | Ant | Vis | Déc | Apl | Vol | Sgf | Acc | Équ | End" in header -> MezSo
        "Drb | Fin | Tir | Pas | Tec | Ctr | Ant | Vis | Déc | Ins | Apl | Vol | Sgf | Acc | Équ | End" in header -> MezAt
        "Drb  | Tir  | Pas  | Tec  | Ctr  | Ant  | Vis  | Déc  | Ins  | Apl  | Sgf  | Agi" in header -> MoSo
        "Drb | Fin | Tir | Pas | Tec | Ctr | Ant | Vis | Déc | Ins | Apl | Sgf | Agi" in header -> MoAt
        "Drb | Fin | Pas | Tec | Ctr | Ant | Déc | Apl | Vol | Sgf | Ctn | Acc | Agi | Équ | Vit | End" in header -> NeufDemi
        "Drb | Fin | Pas | Tec | Ctr | Ant | Vis | Déc | Ins | Apl | Sgf | Acc | Agi | Équ" in header -> AtsAt
        "Drb  | Pas  | Tec  | Ctr  | Ant  | Vis  | Déc  | Ins  | Apl  | Col  | Sgf  | Agi" in header -> MjaSo
        "Drb | Pas | Tec | Ctr | Ant | Vis | Déc | Ins | Apl | Col | Sgf | Acc | Agi" in header -> MjaAt
        "Cen | Drb | Tir | Pas | Tec | Ctr | Vis | Déc | Apl | Vol | Sgf | Acc | Agi | Équ | Vit | End" in header -> AiSo
        "Cen| Drb| Tir| Pas| Tec| Ctr| Ant| Vis| Déc| Ins| Apl| Vol| Sgf| Acc| Agi| Équ| Vit| End" in header -> AiAt
        "Drb| Fin| Tir| Pas| Tec| Ctr| Ant| Vis| Ins| Apl| Vol| Sgf| Acc| Agi| Équ| Vit| End" in header -> AtiSo
        "Drb | Fin | Tir | Pas | Tec | Ctr | Ant | Ins | Apl | Vol | Sgf | Acc | Agi | Équ | Vit | End" in header -> AtiAt
        "Cen  | Drb  | Pas  | Tec  | Ctr  | Apl  | Vol  | Acc  | Agi  | Équ  | Vit  | End" in header -> AilSo
        "Cen | Drb | Pas | Tec | Ctr | Ant | Ins | Apl | Vol | Acc | Agi | Équ | Vit | End" in header -> AilAt
        "Fin  | Tec  | Ctr  | Ant  | Déc  | Apl  | Vol  | Sgf  | Ctn  | Acc  | Équ  | End" in header -> RmdAt
        "Drb | Fin | Pas | Tec | Ctr | Ant | Déc | Apl | Vol | Sgf | Acc | Agi | Équ | Vit | End" in header -> AtaAt
        "Drb | Fin | Pas | Tec | Ctr | Ant | Vis | Déc | Ins | Apl | Col | Sgf | Acc | Agi | Équ" in header -> F9So
        "Fin | Pas | Tec | Ctr | Ant | Vis | Déc | Ins | Apl | Col | Sgf | Équ | Pui" in header -> AtrSo
        "Ctr | Agr | Ant | Crg | Déc | Col | Vol | Sgf | Ctn | Acc | Agi | Équ | Vit | End | Pui" in header -> ApDé
        "Pas| Ctr| Agr| Ant| Crg| Déc| Apl| Col| Vol| Sgf| Ctn| Acc| Agi| Équ| Vit| End| Pui" in header -> ApSo
        "Fin| Ctr| Agr| Ant| Crg| Déc| Apl| Col| Vol| Sgf| Ctn| Acc| Agi| Équ| Vit| End| Pui" in header -> ApAt
        "Drb| Fin| Têt| Tir| Pas| Tec| Ctr| Ant| Vis| Déc| Apl| Col| Vol| Sgf| Acc| Agi| Équ| Vit| End| Pui| Dét" in header -> if (fileName.contains("So")) AtcSo else AtcAt
        else -> intArrayOf()
    }
    return keyCols
}

private fun String.prepend(column: Any, width: Int) =
    "${column.toString().padEnd(width)} | $this"

//private fun score(lines: List<String>) = lines
//    .map { line -> line.prepend(score(line), 6) }
//    .sorted()
//    .reversed()

private fun cleanup(lines: List<String>) = lines.asSequence()
    .filter { it.isNotBlank() }
    .filter { !it.startsWith("| ---") }
    .map { line -> line.substring(2, line.length - 2) }
    .toList()

private fun score(line: String, keyCols: IntArray, coef: Int = 2): Double {
    val values = line.split("| ")
        .asSequence()
        .map { cell -> cell.trim() }
        .mapNotNull { cell -> cell.toIntOrNull() }
        .toList()
    val keyValues = values.filterIndexed { index, _ -> keyCols.contains(index + 1) }.map { it * coef }
    val prefValues = values.filterIndexed { index, _ -> !keyCols.contains(index + 1) }
    val average: Double = (keyValues.sum() + prefValues.sum()).toDouble() / (keyValues.count() * coef + prefValues.count()).toDouble()
    return average.round()
//    return keyValues.averageRounded() to prefValues.averageRounded()
}

//private fun score(line: String, keyCols: IntArray): Pair<Double, Double> {
//    val values = line.split("| ")
//        .asSequence()
//        .map { cell -> cell.trim() }
//        .mapNotNull { cell -> cell.toIntOrNull() }
//        .toList()
//    val coef = 2
//    val keyValues = values.filterIndexed { index, _ -> keyCols.contains(index + 1) }.map { it * coef }
//    val prefValues = values.filterIndexed { index, _ -> !keyCols.contains(index + 1) }
//    val i: Double = (keyValues.sum() + prefValues.sum()).toDouble() / (keyValues.count() * coef + prefValues.count()).toDouble()
//    return i.round() to prefValues.averageRounded()
////    return keyValues.averageRounded() to prefValues.averageRounded()
//}

private fun score(line: String): Double {
    val toList = line.split("| ")
        .asSequence()
        .map { cell -> cell.trim() }
        .mapNotNull { cell -> cell.toIntOrNull() }
        .toList()
//    return toList.median()
    return toList.averageRounded()
}

//private fun List<Int>.median(): Double {
//    return sorted().let {
//        if (it.size % 2 == 0)
//            listOf(it[it.size / 2], it[(it.size - 1) / 2]).averageRounded()
//        else
//            it[it.size / 2].toDouble()
//    }
//}

private fun List<Int>.averageRounded() = average().round()

private fun Double.round() = times(100)
    .roundToInt()
    .toDouble()
    .div(100)

infix fun String.containsInfix(text: String): Boolean = this.contains(text)
