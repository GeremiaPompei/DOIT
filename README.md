# DOIT

L'intenzione della piattaforma **DOIT** è promuovere lo svolgimento collaborativo di progetti innovativi e facilitare l'inserimento delle persone all'interno degli stessi progetti, in base alle loro competenze. Inoltre l'obiettivo è anche quello di realizzare una vetrina dei progetti svolti, in cui un qualsiasi utente sia capace di ricercare e visualizzare le informazioni sui progetti e chi li ha svolti.

## Come funziona
Appena avviato il sito, senza bisogno di autenticarsi, è possibile effettuare una ricerca tra i progetti o gli utenti, oppure, in alto a destra, o cliccando sul menù a tendina nel caso in cui si stia utilizzando un dispositivo mobile, si può accedere alla schermata di login o signin, dove un utente si può autenticare o registrare tramite informazioni personali come **Nome, Cognome, Data di nascita, Sesso, Email e Password**.
Una volta che l'utente si è autenticato verrà indirizzato alla pagina personale, dove, oltre alle informazioni immesse al tempo del signin, sarà possibile assegnarsi autonomamente dei ruoli, selezionando prima la categoria e poi cliccando sul ruolo desiderato. Essi sono:

- **Project Proposer**
- **Program Manager**
- **Designer**

E' presente inoltre un altro ruolo, ovvero il **Project Manager**, che, a differenza degli altri ruoli, non può essere scelto autonomamente dall'utente, ma viene selezionato dal **Program Manager** (questa funzionalità verrà comunque discussa in seguito).
In questa pagina, oltre ad aggiungere ruoli, è possibile contattare gli amministratori e fare il logout.

Per ogni tipo di ruolo sono comunque disponibili le seguenti funzioni:
- **List projects**: tramite questo pulsante verremo reindirizzati alla pagina in cui sono contenuti tutti i progetti a cui quel ruolo sta partecipando attivamente. Ovviamente è possibile selezionare qualsiasi progetto per visualizzarlo nel dettaglio.
- **History**: tramite questo pulsante verremo reindirizzati alla pagina in cui sono contenuti tutti i progetti a cui quel ruolo ha lavorato, ma che sono stati chiusi
- **List categories**: tramite questo pulsante verremo reindirizzati alla pagina in cui sono contenute tutte le categorie che appartengono a quel ruolo. Ovviamente è possibile selezionare qualsiasi categoria per visualizzarla nel dettaglio.
- **List notifications**: tramite questo pulsante verremo reindirizzati alla pagina dove sono presenti tutte le notifìche di quel ruolo.
- **Manage category**: tramite questo pulsante verremo reindirizzati alla pagina dove sono presenti tutte le categorie che appartengono e che non appartengono a quel ruolo. E' possibile sia aggiungere che rimuovere le categorie semplicemente cliccando sul loro nome, ma non è possibile rimuovere una categoria se è l'ultima rimasta, o se quel ruolo sta partecipando attivamente ad un progetto con quella categoria.
- **Remove role**: tramite questo pulsante viene rimosso il ruolo. Questa operazione è possibile solamente quando il ruolo non sta partecipando attivamente a nessun progetto

### Project Proposer
Il **Project Proposer** si occupa della creazione del progetto. Questo ruolo ha le seguenti funzioni:
- **Create project**: tramite questo pulsante verremo reindirizzati alla pagina di creazione del progetto, dove, una volta immessi nome e descrizione e selezionata la categoria è possibile effettivamente creare un progetto.
- **Manage partecipation request program manager**: tramite questo pulsante verremo reindirizzati alla pagina in cui sono contenute tutte le richieste di partecipazione da parte dei vari **Program Manager**. Selezionata una qualsiasi richiesta, è possibile accettarla, oppure rifiutarla spiegandone il motivo.
- **Remove project**: tramite questo pulsante verremo reindirizzati alla pagina in cui sono presenti tutti i progetti che possono essere eliminati. Un progetto può essere eliminato se ancora non ha un **Program Manager**.

### Program Manager
Il **Program Manager** si occupa di creare un team per un progetto proposto in cui viene richiesto il suo supporto e gestisce i membri del team. Gestisce l'apertura e la chiusura di partecipazione al team. Questo ruolo ha le seguenti funzioni:
- **Send partecipation request to project proposer**: tramite questo pulsante verremo reindirizzati ad una pagina in cui sarà possibile selezionare le varie categorie di questo ruolo, e per ogni categoria verranno mostrati tutti i progetti a cui è possibile inviare una richiesta di partecipazione con la qualifica di **Program Manager**.
- **Manage partecipation request designers**: tramite questo pulsante verremo reindirizzati alla pagina in cui sono contenute tutte le richieste di partecipazione da parte dei vari **Designer**. Selezionata una qualsiasi richiesta, è possibile accettarla, oppure rifiutarla spiegandone il motivo.
- **Manage team registrations**: tramite questo pulsante verremo reindirizzati ad una pagina in cui sono presenti tutti i progetti a cui questo ruolo sta partecipando, e, per ogni progetto, sarà possibile aprire o chiudere le iscrizioni dei **Designer**.
- **Set project manager**: tramite questo pulsante verremo reindirizzati ad una pagina in cui, una volta selezionato il progetto giusto, verrà presentata la lista di tutti i **Designer** che partecipano al progetto, e sarà possibile sceglierne uno e assegnartgli il ruolo di **Project Manager**.
- **List my partecipation request**: tramite questo pulsante verremo reindirizzati ad una pagina in cui sono presenti tutte le richiesti di partecipazione inviate ed il loro rispettivo stato.

### Designer
Il **Designer** può inviare richieste di partecipazione ed aggiungere delle esperienze passate per incrementare le sue possibilità di essere scelto. Questo ruolo ha le seguenti funzioni: 
- **Send partecipation request to program manager**: tramite questo pulsante verremo reindirizzati ad una pagina in cui sarà possibile selezionare le varie categorie di questo ruolo, e per ogni categoria verranno mostrati tutti i progetti a cui è possibile inviare una richiesta di partecipazione con la qualifica di **Designer**.
- **Manage pregress experience**: tramite questo pulsante verremo reindirizzati ad una pagina in cui sarà possibile inserire un'esperienza di lavoro inserendo una data di inizio, una data di fine ed una descrizione.
- **Remove project**: tramite questo pulsante verremo reindirizzati alla pagina in cui sono presenti tutti i progetti da cui ci si può eliminare. Ci si può sempre eliminare da un progetto, e, una volta eliminato, viene notificato il **Program Manager**.
- **List my partecipation request**: tramite questo pulsante verremo reindirizzati ad una pagina in cui sono presenti tutte le richiesti di partecipazione inviate ed il loro rispettivo stato.

### Project Manager
Il **Project Manager** è colui che gestisce le varie fasi del progetto e valuta i **Designer**. Questo ruolo ha le seguenti funzioni:
- **Manage project state**: tramite questo pulsante verremo reindirizzati ad una pagina in cui saranno presenti tutti i progetti a cui si sta lavorando con il ruolo di **Project Manager** e, per ogni progetto, sarà possibile avanzare o retrocedere lo stato. Gli stati sono 3:
  - **Initial**
  - **In progress**
  - **Terminal**
- **Evaluate desginer**: tramite questo pulsante verremo reindirizzati ad una pagina in cui, una volta selezionato il progetto giusto, verrà presentata la lista di tutti i **Designer** che hanno partecipato al progetto, e sarà possibile valutarli (la valutazione deve essere tra 1  e 5). Una volta valutati tutti i desginer, sarà possibile premere il pulsante **exit projet** per terminare il progetto.
- **Remove project**: tramite questo pulsante verremo reindirizzati alla pagina in cui sono presenti tutti i progetti da cui ci si può eliminare. Ci si può eliminare da un progetto solo se è disponibile un altro **Designer** per diventar **Project Manager**.
  

https://doit-ids.herokuapp.com/
