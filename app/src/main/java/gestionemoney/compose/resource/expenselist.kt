package gestionemoney.compose.resource

// this is going to be replaced by the database
val expenselist = listOf(
    "H&M",
    "Prada",
    "Jack & Jones",
    "Gucci",
    "H&M",
    "Prada",
    "Jack & Jones",
    "Gucci",
    "H&M",
    "Prada",
    "Jack & Jones",
    "Gucci",
    "H&M",
    "Prada",
    "Jack & Jones",
    "Gucci"
).groupBy {
    it
}.toSortedMap()