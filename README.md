# Creación de Firebase Chat
### Chat Login, EventBus
Vamos a crear la parte de Event Bus para inicializar y registrar nuestros eventos en esta parte
Hasta el momento tenemos una comunicacion en una via
De la vista al presentador
Del presentador al Interactuador
 Del Interactuadro al Repositorio

 Pero no hay una forma que esa información regrese desde el repositorio hacia el presentador y luego a la vista


Event Bus, patron de software
Comunicar eventos a traves de bus de datos


EventBus, Interfaz, es la que nos dices que nos va ainteresar dentro de este EventBUs
Interface EventBus

REgistrar al bus a travez de un objeto
Deregistrar, con suscriptor , y presentador y contact list por eso es objeto
post queremos publicar un evento

Ayuda para qeu cuando cambien la clase EventBus no se ha tan ruidosa

Ctreamos una clases estatica que me haga una unica instancia
tenemos una única instancia en nuestra aplicación



Creamos la clase GreenRobot Event Bus , definiendo las llamadas en base a la libreria EventBus de registrar . unregister y post creados

Creamos un EventBus de GrenRobot:evente
y trabajamos como singleton
con clase statica y dentro de ella una variable privada estática y final llamada  INSTANCE
 y un metodo que sea getInstance y al ser Estatico me permite obtener una instancia de esta clase que estoy escribiendo
al llamar a getInstance obtengo la instancia que me interesa, solo llamo una osla vez con final


y al constructor le definmos como un EventBUs de green robot



Con esto me garantizo que cuand las cosas cambien de la liberria o que por alguna razon quiera otra, mi codigo cambia menormente
solo cambio la implementacion


Login Event Bus usando la librería (CHAT LOGIN)

Crearemos constantes en Login Event

Recordamos hablamos de 4 casos,singnIn sgnUp success Error
agregamos
uno cuando intento recuperar una sesion no hay una sesion abierta
y entonces en base a eso solicito el login pero primero reviso
por eso hacemos una variable para cada uno de estos casos

Todos los posibles eventos, constanstes y lo encapsulo dentro de una sola clase para que sea
sencillo
pongo el tipo de evneto y el string para el mensaje de error que existe
ESTOS EVENTOS SE USAN EN EL REPOSITORIO



Luego vemos el repositorio para emitir los eventos


En el LoginrepositroioImpl
hacemos un postEvent con el tipo de evneto y un mensaje de erorr
Instanciamos un evento LoginEvent
asignamos el tipo , set
vemos que el error sea diferente de null
    y mandamos el error

Instanciamos eventBus, llamando a grenn robot EventBus getInstance
y publicamos el evento Usando, event.post(loginEvent)

hacemos otro con postEvent pero solo el tipo de evento
    llamamos al primero con el tipo pero en el mensje con null
    postEvent(type,null)

En vez de l as llamadas al LOG en checkSession
hacemos un postEvent(LoginEventonFailedTorecoverSession)

SIGIN success
postEventLoginEvent.onSignInSuccess

SIGUP success
postEventLoginEvent.onSignUPSuccess



Ahora vamos arecibirlos en el presentador hacemos un onEventMainThread con LoginEvent event
en LoginPresenter ponemos esto, en la interfaz asociado a la librería, lo agragamos
en la interfaz de tal forma que cuando no este asociado a la librería ya no tenga un problema y pueda
mantener el mismo nombre
y si lo quiero modificar lo modifico desde la interfaz


Como lo agregue en LoginPresenter tambien lo agrego en LoginPresenterImpl

y hacemos en onEventMainThread
un switch dependiendo del tipo de evento yo hago algún suceso

case LoginEvent.onsingInSUcess:
onsigninSucess()
break,

con onFailedToRecoverSession()
    revisar cuando no hay una sesión


Pero necesitamos registrar el presentador al bus para escuchar por eventos
entonces registramos
en LoginPResenter
    void onCreate
 y lo sobrecargamos en nuestra LoginPresenterImpl



 Vmos a LoginPresenterImpl
    definimos una EventBus de la mía Interfaz
 y luego inicializamos
    eventBus=GreenRobotEventBus,getInstance()

en onCreate para qu regiustre al presentador para escuchar
    eventBus.register(this)
en onDestroy luego de volver nula la vista vamos a
    eventBUs.unregister(this)


   Recordando .
   A partir de la actividad voy a estar checkeando por la autenticacion

   EN loginActivity
    antes de checkear por la autenticacion loq ue vamos a hacer es
    en el peresnter hacemos la llamda a oncreate()
            loginPresenter.onCreate()

    Hacemos onDestroy()
        y llamamos a ondestroy del presentadr y de esta forma evito el memory lick

Volvemos al presentador
    en onFileToRecoverSession
    hacmeos algo simirlar a lo de los errores, pero no mostramos un error si no un
    mensaje en ellog


EN EL SIGN IN Y SIGNUP

    creamos los usuarios
        testuser1@lextime.com           lexuce2345
        testuser2@lextime.com           lexuce2345
USAMOS LOGINREPOSITORYIMPL
EN el código vamos a hacer cosas en el repositorio como variables globalres
nos interesa tener una REFERENCIA de datos que se utulizara para la autenticación
y una hacia los usuarios, en particular hacia mi usuario

nos vamos a apoyar en el helper de lógica de dominio que construímos como lo son getDataReference y getMyUserRefrence
para poder escribir los datos

Inicilizamos en el contructor

        dataReference= helper.getDataREference;
    para la refrencia de los usuarios
        dataReference= helper.getDataMyUserReference;


    Noramos que la primera vez cuando yo no tengo una sesión MyusrRefrence, va a ser nulo
    porque no tengo un correo asociado, por lo que tenemos que hacer otra asignación


Trabajamos en el inicio de sesión

    datareference.auth con password con los parametros que recibimos
        si está autenticado
            actualizamos la referencia de usuarios,
            myUserReferences es la referencia de mi usuario
            y a esta referenica le vamos a decir que tenga un listener para un solo evento y hacemos un new valueEvent

            Nos interesa en el momneto que haya datos (onDtaChange)  e instanciamos un nuevo usuario
            Verificamos si al iniciar sesion fui a traer los datos y el resultado es nulo tengo que ir a agregarlo
            escribitmos el email a traves del helper y este email
                lo creamos con un nuevo usuario
                y por ultimo a mysuer refernce le hago un setValueel usuario que he creado
            ya que tengo el usuario en mi backend listo
            Luego cambio deswde mi helper
                helper.changeUserConnectionStatus(User.ONLINE) que este online


     LUego Creamos el Modelo del usuario

            Un email,
            un boleano si esta online
            un Map de string y booleanos para los contactos
                alexis  onilne
            online y offline staticos

            todos los gets y sets
            firebase se va a poder integrar on el modelo de datos


   Refactorizamos algo en el firebase helper
            en NotifyContactsOfConnectionChange(USER.OFFLINE, TRUE)
            EL estado del contacto para verificarlo

tenemos el mismo dataReference pero llamamos a create User y enviamos email password y tenemos
un VlueResultHandler, en este caso estamos recibiendo el resultado del inicio de sesión y postearé si este
fue exitoso
además que estoy creando un usuairo pues mando a mi signIn y mis mail y password

AHORA EN EL CHECKSESION

    Tenemos qeu ver si existe una sesión, y si en caso existe redirigimos al usuario para que mantenga esa misma sesion
    entonces revisamos si el usaurios esta autenticado si no entonces le mando que no pudimos
    postear un evento
    la difrencia es que no usamos email y paswordla parte de onAutenticated (en SIgnin)
        si no que uso la referencia y con esta voy a ir a traer al usuario de la base de datos
        y si esta creada listo, eso es todo cambio a helper en online y nada mas
        si no entonces la crea (currentUser==null) creo email con getAuthUserEmail



Para el layout de los contactList

    Imagen
    COrreo
    Estado de la persona

 2 archivos de layout Coordinator Layout
    toolbar



    Procedimiento
        Strings
            los valores
        Contenido de un contacto*(Relative Layout)
            agregamos nuestro circleImageView 48dp, 48dp (dimensiones)
                le damos un margen solo hacia la derecha marginRight
                cosas que permiten personalizar, con app
                le damos un color
                    civ border widht #1dp
                    border color #ffff
            Agregamos textView para el email
                Large text

            Agregamos textView para el estado
                medium Text



Video de contactList
Layout*(Video 2)

    Activity Contact list
        Dentro de un coordinator layout
            creamos la actividad con el contexto que necesitamos
        Creamos estilos con un valor asociado
            styles.xml
                popUpOVerlay
                    parent"ThemeOverlay.AppCompat.Light

    Entones tengo
        Corrdinator layout
            Admnistrar todo lo que vaya dentro
        AppBarLayout
            Administrar el toolbar
        Recycler View
            Donde coloco todo el contenido

        FloatingButton
            tinte para que se note

Nota si tengo duplicada el tema, voy a styles y creo el mio
    AppTheme.NoActionBar
        me permite tener un item de window action Bar que no teien la bandera verdadera
        y otro que va a tener true , en window no Title


    Dentro de la actividad en el manifest podemos crear
    <activity andoid :name Nombre Actividad>
    android:label @String
    android theme ,el estuilo creado
    Toolbar no tiene nada pero podemos ponerle nosotros mismo nuestro lab

    en Estilos podemos crear AppTheme.AppBarOverlay parent ActionBar


Arquitectura MVP
    Video3
        Creamos nuestros contratos o interfaces para poder tener la parte de arquitectura

        ContacListView interface
            Que esta reaccione cuando hay un contacto agregado y reciba este usuario
            CUando el contacto es cambiado, cambio su estado
            Cuando borramos el contacto

        Para el presentador una interfaz

        Contact List Presenter interface
            Eventos que reaccionen en el presentador
                    onCreate
                    onDestroy
                para hacer cierta configuracion, pero como va a estar trabajando con firebase y va a tener una conexion en tiempo real
                no quiero tener esta conexion abierta siempre
                tenemos un evetno de
                    onpause
                    onResume
                Accion de cerrar sesion, y borrar un contacto con el mail, metodo para manejar los eventos
                 recibidos
                    sigOff
                    removeContact(mail)
                    onEvnetMainThread (ContactListEvent)
                 Quiero el mail del usuario que esta logeado para que aparezca en el toolbar
                    String getCurrentuserEmail()

         Interfaz para el interactuador ContactListInteractor
                void subscribe*()
                void unsubscribe()
                void destroyListener()
                void removeContact(String email)

            Ademas de esto necesito acciones para la sesion y aqui es donde se comenta que va en cada clase
                lo separamos pmetodos para manejar la sesion
                    Interfaz    ContactListSessionInteractor
                                    void signOff()
                                    String getCurrentUserEmail()
                                    void changeConnectionStatus( boolean online)
                    Podria haber separado este remove contact a un tercer interactuador
                        y tengo que ver hasta que punto puedo cambiar esta parte

            Por ultimo creamos un
                ContactListRepository interfaz
                    la que va a responder ante los eventos puestos anteriormente
                        void signOff(
                        String getCurrentUser *(email
                        void removeContact*()
                        void subscribe
                        void unsibcribe
                        void destroyListener
                        void changeConnectionStatus


ContactListView MVP Video 4
Ahora trabajamos por la actividad,
                    Esta implmenta el contrto de la vista
                    oncontactAdd..... etc
                Tenemos unos atributos basicos como
                    private ContatcListPresenter. presenter

                Usamos butterknife con el toolbar recycler view y el floating action Button

                Para el FloatingButton
                    @OnClick(R.id.Button)
                    public void addContact(){
                    //esto es lo que hace el button
                    }

                Me interesa onDestroy
                    presenter.onDestroy
                Em interesa OnPause
                    presneter.onPause
                me Interesa OnResume
                    presenter.onrexsume

                y en onCreate pongo
                    presenter.onCreate
                    toolbar.setTitle(preseneter.getCurrentUserEmail\
                    setSupportActionBar(toolbar(


Video 5
    ContactList ContactListAdapter
            Cramos un adapatador para el listado (RecyclerView)

            en adapters
                interface onItemClickListener
                                    Tenemos dos eventos,
                                                        cuando hay un click
                                                        Cuando hay un click largo
                Class ContactListAdapter herede de RecyclerViewAdapter con un ViewHolder
                    Creado en esta mismo apartado con todos su metodos
                        Guardar los datso de alguna forma
                            Listados de usuarios
                                ListUser contactlist
                            Formap para cargar las imagenes
                                ImageLoadin imageLoading
                            OnItemClikcListener
                                onItemClickListener para manejar los eventos de click largo o corto

                        Creamos un constructor con los datos que hemos definido

                        Implementamos los metodos en el view holder,
                            queremos un view holder a partir de la vista

                                View view LayoutInflater.....
                                return La clase ViewHolder(con esta vista)
                        onBindViewHolder
                            Requiere definir los elementos dentro del holder,gracias a butterknife traemos los atributos que necesitamos

                        En el viewHolder Pngo la vista y los atributos definidos en el layout
                               defino una Vista view
                               y en el constructor mando a definir la vista que he obtenido con la vista que tengo como privada

                           Queremos los mteodos que me permiten manejar el click
                                Como vemos aobre la vista (view)  adentro del click voy a llamar al metodo que necesito
                                y como mi interfaz de click lo que hace es recibir un usuario, entonces recibo el usuario como inal dentro del clickListener
                                y tambien nencesito un listener interfaz del onitemClickListener

                                private setClickListener(final User user,final OnItemClickListner listenter){
                                    view.setOnClickListener(new View.onClickListener{
                                    public onClikc(view v){
                                        listner.onItemClick(user)(
                                    }
                                    }
                                }

                                Lo mismo para el onLongClickListener
                Hasta aqui he construido el metodo

            OnBindView Holder
                Es el que me hva a permitir a mi crear y declarar un usuario de acuerdo a la posicion
                    defino un la instancia del click que he decidido, con
                    holder.setClickLIsterne(usuario obtenido, onItemClikc)
                    pero ademas este es lugar donde coloco los valores necesarios para que los campos tengan algo

                            holder.txtUser.setTest

                para ver si esta online
                    boolean online user.isOnline()
                    String status = online? "online":"offline"
                    int color= online ? Color.GREEN:Color.RED
                           holder.txtUser.setTest
                           holder.txtUser.setTest
                           holder.txtUser.setTest

                    SI YO QUISIERA LEEER ONLINE Y OFLINE A PARTIR DEL XML, necesito un contexto
                    pero si tuviera mas cosas realcionadas con strings es bueno usar y recibir el contexto

           Para las imagenes lo que hacemos es
                        imageLoader. load(lugar donde se cargaImageView donde se carga, URL)
                            imageLoader.load(holder.imgAvatar,"URL")
                            e implementamos el metodo de carga




CONTACT LIST


        Video2 Layout

             Listado de elementos
                Estado del layout
                    Agregamos los strings con el patrón de Actividad.accion. menú
             Floating ActionButton


     Creamos un content contact
            que es lo que tendremos dentro de contactList
                poniendo las dimensiones de circle Image View


        Creamos un activity contact list, asociado a la actividad
          Coordinator Layout, administra todo lo que lleva dentro

            AppBarLayout, administra el toolbar
            Toolbar, colores y menu
            RecyclerView, coloco todo el contenido
            FloatinActionBottom, tiene tinte blanco y su respectivo icono

        Craemos nuestro tema
            editando el estilo, NoActionBar
                me permite un item de windows action bar que no tiene la bandera en FALSE
                window no Title TRUE
                Y AÑAdimos dentro de la actividad label y el theme, que nosotros le estamos colocando


Contact List Estructura MVP Video4

        Dentro de RecylcerView
            Quitamos Scrollbars dentro del RecyclerView porque no tenemos todav
            ia datos



        ESTRUCTURA MVP

            Contratos o Interfaces para que podamos tener toda la parte de arquitectura funcionando
                ContactListView
                    Essperamos que la vista reaccione cuando
                        hay un contacto agregado, addContact
                        cuando cmabio su estatus, ContactChanged
                        cuando borramos el contacto, ContactRemoved

                Presenter,va a tener varios eventos al reaccionar
                    ContactListPresenter
                        onCreate,Configuracion
                        onDestroy,Configuracion
                        OnPause     Pero ademas como trabajo con firebase, y va a tenr un conexion en tiempo real, no va a quedar esa conexion abierta siempre
                        onResume

                        signOff             Accion de cerrar sesion
                        removeContacts      Accion de borrar contacto, mediante el email que estamos creando
                        onEvnetMainThread   Metodo para manejar los eventos Recibidos, Mediante un CONTACTLISTEVENT

                        String getCurrentUserEmail  para tener el mail y ponerlo dentor del toolbar



                    ContactListInteractor, este interactuador va a tenre dos acciones
                        Todos estan puestos para la lista de contactos
                            subscribe
                            unsubscribe
                            destroylistener
                            removeContacts
                     Acciones para la sesion
                     ContactListSessionInteractor
                        signOff
                        getCurrentUserEmail
                        changeConnectionStatus, indicando si es ONline o no

                    Le dejo en dos interactuadores, no mas


                Para Terminar

                ContactListRepository
                    signOff
                    getCurrentEmail
                    removeContact
                    Metodos para suscribirse
                    destroyListener
                    subscribeToContactListEvents
                    unsubscribeToContactListEvents
                    changeConnectionStatus

                Esto es lo mínimo que debería implementar las clases para que funcione mi aplicación


Contact List MVP Activity
        Continuamos y trabajamos en ContacListActivity para implementar el contrato de la vista
        implements ContactListView

        Creamos un ContactListPresenter presenter
        Como requisito mínimo

    Asociados al ciclo de vida de la actividad, para luego implementarlo
                    OnDestroy.
                        presnter.onDestroy()

                    onResume,
                        presnter.onResume()
                    onPause,
                        presnter.onPause()

            En onCrete
                    presenter.onCreate

            Nota. no esta todavía para implmementar.

        Voy a implmenatar el presentador, de tal foramq ue la vista ya tenga con quien intercatuar,


    Contact List Adapter

       Implemantamos un Adaptador para el listado, debemos ver cuando hay un click en el evento, o un click largo
        Este herada de RecyclerViewAdapter;ContactListAdapter.ViewHolder:
            Implemenatmos los metodos

        Creamo una lista contacList, lista de contactos
        Imagen          la imagen que por el momento es una interfaz
        onItemListener  para manejar los clicks

        Creo mi constructor, con todo lo que necesito

        Necesito un viewHolder a partir de la vista
            View view=LayoutInflater.from(pareng.getContext()).inflate(R.layout.contectcontact), parent,false=
            return new ViewHolder(view).//retornamos el viewHolder de la vista de esta misma clase a partir de esa vista, notemos que viewHolder esta definida en esta vista
        OnBindViewHolder. realizo alguna accion sobre el metodo getItemCount(contacList.size)
        Definir los elementos dentro del holder, y estas inyecciones (Butterknife lo pongo dentro del viewHolder
        y como vamos a definir el click entonces hacemos una variable privada View, view y en el constructor lo hago

        Metodo qeu me permite manejar el click
            private
                view.seOnClickListener (new View.Oncl...
                    void Onclick
                        mi interfaz es con un usuario, por loq recibe el usuario, y la interfaz del usuario

                        listener.onItemClik,
                        view.setOnLongClickListener

                OnBindView Holder
                me permite asignar los valores, declaro un usuario, a partir de contactLIst.get(positrio)
                holder.setClickListner(user,onItemClickLisnter

                este es elugar donde veo que los valores tengan algo, cmapos del viewholder tengan algo
                holder.txtUser.setText(...

                String status= online?"online": "offline";


                Para imageLoader, necesitamos, el imageView sobre el que caey la url, y que sea general no solo un circleImageView, si no ImageView


        ViewHolder hereda de RecyclerView.ViewHolder
        con su respectivo dato


Video 6 Contact List Image Loading y Prueba de Adapter

        Creamos el IMageLoadinDentro de libs

        Creamos una clase GlideImage lOader que implmenta ImageLoader

        Definimos n RequestManager de parte de Glide

        EN el constuctor, debería recibir directamoent pero en vez de eso vamos a recibir un contexto.
        algo interesante de esto , es que este contexto podría ser un Fragment, Activity, COntext y se pega bien al ciclo de vida de los elementos

        y el método load dentor de Glide , lo que va ah acer es

        requestmanager.load(url).into(imgAvatar)


        Volvemos a la actividad y veo que ya tengo un adapatador, y uso metodos para buscarlos
        setupToolbar()
            metodos el toolbar
        setupAdapter
            ImageLoader loader' new GlideImageLoader(this.getAplicationContext)(le mande un conetxto
            metdoos del adapatador , variable ContactListAdapter. lo inicializamos aqui, un listado vacioi de usuarios, un imageLoader,lonItemClickListerne
            para esto poner this, tengo que implmentar el onITemClikcListener dentor de esta actividad
        setupRecyclerView
            Definimos los datos el recyclerview
              private void setupRecyclerView() {
                    recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
                    recyclerViewContacts.setAdapter(adapter);
                }


        Para probarlo comento todo el presenter porque no tengo nada aun
        Para ver un listado con algo de informacion , hacemos informacion Dummy

        User user....
        adpater=new ContactListAdapter(ArrayasList( new User[]{user}), ..loader, this)


    Ahora vemso que no tenemos una foto, pero necesito cargarlo de algún lugar,
        Helper, para una clase nueva, llamandola AvatarHelper, método estatico qeu perimte devolverun String , a partir del emial
        en base a este email, vamos a ver cual es el url Correspondiente,

        nos apoyamos en gravatar, definimos un md5 método que calcula este a partir de un string
        y me sirve para obtener el resultado
        y devulvo es baseUrl, concatenado con el md5 del email y el paramtero que sea de tamañan 72, ?s=72


            public static String getAvatarUrl(String username) {
                    return GRAVATAR_URL + md5(username) + "?s=72";
            }


        Con esto podemos poner dentor del adapatador (onBindViewHolder) imageLoader.load(holder.imgAvater, AvatarHelper.getAvatarUrl(email).


            imageLoader.load(holder.imgAvatar, AvatarHelper.getAvatarUrl(email));





ContactList Presenter y Event

        Después de haber hecho las pruebas en la vista, nos corresponde hacer las implmentaciones de todas las clases,

        Entonces implementamos contactlistPresenter, desde la clase ContactListPresnerterImpl

        Con estos métodos listo puedo trabajar

        necestio un EventBus, para poder detectar los evetnos
        necesito un vista, para comunicarme con la actividad
        necesito una instancia de cada Interactuador.
            ContactListInteractor listInteractor
            ContacListSessionInteractor sessionInteractor

        Dendtro del constructor recibo unicamente la vista, eventBus. el getInstance y a las otras dos
        variables les iniciazo como su ContactListInteractorImpl,

        DEBEMOS DARNOS CUENTA QUE USAMOS ESTE DATOS PARA INSTANCIARLO LO CREAMOS EN UNA INSTANCIA DE SU IMPLMENTACION ASI PODEMOS HACER USO DE CADA IMPLEMENTACION QUE NCESITAMOS




    Creamos en el presentador ir constryyendo uno a uno cada uno de estos métodos

        onPause.
            que la sesion se ponga en pausa, y se desinscribirnos
                sessionInteractr.changeConnectionStatus(user.Offline)
                listInteractor.unsubscribe()
        onResume
            que al resumir se ponga a subscribir y cambiar el estatus a online
                sessionInteractr.changeConnectionStatus(user.Offline)
                listInteractor.unsubscribe()
        onCreate
            que al crear el presentador que se registre dentor de EventBus,
                eventBus.register(this)

        onDestroy
            Que se haga opuesto. nos desinscribimos del eventBus, la vista la volvemos null, y listInteractor destruimos el listener, para evitar el memory lick
                eventBus.unregister(this)
                listInteractor.destroyListener()
                view= null

        sigOf
            nos vamos a ofline, nos desubscribirnos, destruir el listener y por ultimo, llamar al signoff de la session

        getCurrentUserEmail
            hacemos un getCUrrentUserEmail

            return sessionInteractor.getCurrentUserEmail

        removeContact
            algo similar pero con contactListInteractor,
                listInteractor.removeContact(email)

        onEventMainThread
            los evetnos que he definido en ContactEvent
            Traemos el usuario a partir del evento, que este me lo envia el repositorio
            y hago el evento getType, hago un swith a partir del evento del evento GetType, con mis tres posibles escenarios
            y en cada uno de ellos llamo a mi metodo definido

            @Override
                @Subscribe
                public void onEventMainThread(ContactListEvent event) {
                    User user = event.getUser();
                    switch (event.getEventType()){
                        case ContactListEvent.onContactAdded:
                            onContactAdded(user);
                            break;
                        case ContactListEvent.onContactChanged:
                            onContactChanged(user);
                            break;
                        case ContactListEvent.onContactRemoved:
                            onContactRemoved(user);
                            break;
                    }
                }






        private onContactAdd (User user)
            si la vista no es nula etnonces usarmos el metodo de la vista
                view.onContactAdd(user)

        private onContactChanged (User user)
            si la vista no es nula etnonces usarmos el metodo de la vista
                view.onContactChanged(user)

        private onContactRemoved (User user)
            si la vista no es nula etnonces usarmos el metodo de la vista
                view.onContactRemoved(user)



    Denttro de ContacListEvent
        Para identificar estos tres eventos para identirifacar que tipo de evento tengo
            Cuando añado un contacto onContactAdd
            Cuando cambio un contacto onChangeContact
            cuando elimino un contacto onContactRemoved
        en la myoria de casos necesito un Usuario
        un Entero para saber que tipo de evento es,
        con sus respectivos getter y setters



         Seguimos a ContacListPresenterImpl
    CON ESTO TENGO LISTO MI PRESENTADOR, EL PRESENTADOR ESTA INSTANCIANDO AL INTERACTURADOR, PERO EL INTERACTURADOR AUN NO TIENE NADA

Debemos tener en cuenta que usaremos EventBus de mi paquete de librerias. y luego la instancio a la de greenRobot, en PresenterIMpl, con esto puedo poner mis propios metodos para usarlos dentor de greenRobot


















