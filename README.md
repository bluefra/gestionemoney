# gestionemonetaria compose
### Logs
## grammatica
  event-type = [3-4] caratteri di descrizione della classe_event-name
## firebase Logs
### Connessioni/Funzionamento app
• verifica connessioni dell’autenticazone, dell’Info DB e dello user DB con
  annesi errori e tempi di connessione.
• log del successo o del fallimento dell’ eliminazione di una categoria su
  DBUserConnection, sia per il DB sia per il locale, per controllare se l’errore 
  è locale o a livello di DB.
• verifiche di connessioni non andate a buon fine.
• errori nel caricamento di dati da parte di Authentication.
• tempo di caricamento per aprire la home-page.
### raccolta dati sull'attività
• aggiunta con successo di una spesa, salvando anche il giorno della settimana 
  in cui `e stata aggiunta.
• aggiunta con successo di una categoia, salvando anche il giorno della settimana 
  in cui `e stata aggiunta.
• differenza in giorni dall'ultima spesa.
• differenza in giorni dall'ultima categoria aggiunta.
• tempo medio fra una spesa e l'altra.
• tempo medio fra una categoria e l'altra.
## Logs su DB
sotto permesso dell'utente è possibile raccogliere dati per:
### valutazione delle scelte progettuali
• Lunghezza media dei caratteri usati per le categorie, per verificare se il limite
  impostato sia sufficiente
• Lunghezza media dei caratteri usati per le spese, per verificare se il limite
  impostato sia sufficiente
### raccolta dati di marketing
• media di spesa per ogni categoria
• numero medio di spese per categoria
• tempo trascorso dall'ultima creazione di una categoria
• tempo trascorso dall'ultima creazione di una spesa
