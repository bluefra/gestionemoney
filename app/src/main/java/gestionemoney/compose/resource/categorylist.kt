package gestionemoney.compose.resource


// this is going to be replaced by the database
val categorylist = listOf(
    "Abbigliamento",
    "Alimentari",
    "Uscite Varie",
    "Ristoranti",
    "Abbigliamento",
    "Alimentari",
    "Uscite Varie",
    "Ristoranti",
    "Abbigliamento",
    "Alimentari",
    "Uscite Varie",
    "Ristoranti",
    "Abbigliamento",
    "Alimentari",
    "Uscite Varie",
    "Ristoranti"
).groupBy {
    it
}.toSortedMap()