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



Implementación del interactuador Video8

    En ContactListSessionInteractorImpl
        tengo un ContactlistRepository y sobre este vamos a actuar

        En este repositorio necesitamos que este inicializado
        por el momento no lo recibimios en el constructor nuestro constructor, esto no es la mejor de las ideas cuando se testea, por lo que se usa IndependenceInjection



        signOff
            repostitoySignoff
        getCurrentEmail
            repository.getCurrentUserEmail
        changeConnectionStatus
            repository.changeConnectionStatus


    Dentor de COntactListInteractorImpl
        creo mi ContactListInteractor
        con mi ContacTLIstRepository
        lo creo en el contructor pcomo el anterior

         llamo a los metodos
            subscribe
                repositroy.subscribeToContactList

            unsubscribe
                repository.unsbsrctitToContactLIstEvnet
            destroyListener
                repositoy.destroyLIstener
            removeContacts
                repository.removeContact


Video 9 Repository

            ContactListRepository
                Espero tener un FierbaseHelper, que definí al inicio
                ChildEvetnLIstener, contactEventLIstener

                EN el constructor,
                    me pongo el helper y FirebaseHelper.getInstance
                EN signOff
                    helper.signOff
                En getCurrentUserEmail
                    helper.getCurrentUserEmail
                En removeContact
                    pero necesitmo obtener el correo del usuarioactual
                        String currenteUser=helper.getAuthUserEmail

                        helper.getOneContactREference(currenteUser,emailRecibido).removeValue(), de tal forma que pueda borrar de mi listado de contactos a la persona quq qiuero borrar
                            pero ademas quiero borrar a la otra persona de sus contactos
                        helper.getOneContactReference(email,currentUserEmail).removeValue

                En DestroyListener
                    lo que requiere es que el listener actual sea nulo
                    contactEventLIstener=null
                 En SubscribeToContactListEvent
                    contactEventLister

                    Vamos a revisar que sea diferente de null, y luego,
                    helper.getMyContactsREference.removeEvListener(contactEventListener)

                    Pero primero vamoss a revisar si contactEventListener == null, y luego abajo hacemos la suscribcion
                        if(contactEventLIstenre=null){
                            contactEvnetListener=new ChilEventListner  //inicializo a este elemento de Firebase
                                    //Recordemos que tenemos una estructura jerarquica en donde, al acceder a los elementos de los contactos voy a tener el usuario: diagonal / contactos y bajo este nodo voy a tener el usaurio "/" contactos y bajo este nodo el email y su estatus
                                    //alexis@gmaill.com
                                        //sistemas@galil
                                            online
                                onChildAdded, cuando se agrega
                                    //definimos un email, que va a venir de la data de fierbase, (dataSnapshot), getKey, recoredemos que firebase no guarda los puntos y hago un replace para ponerlo en el formato en qel que espero mostrarlo
                                    email=email.replace("_",".")
                                    String email=dataSnapshot.getKey()
                                    //definimos un online dataSnapshot,getValue(), este getValue tengo qeu castear a Boolean y luego puedo hacer un booleanValue
                                    boolean online = ((Boolean)dataSnapshot.getValue()).booleanValue();
                                    DEfino mi usuario,
                                    User new User
                                    user.setEmail(email)
                                    user.setOnline(online)

                                    Cuando ya termine de agregarlo puedo publicar un evento
                                    y llamo a post(TIpode Evento, el Usuario(user)
                                        private void handleContact(DataSnapshot dataSnapshot, int type) {
                                                String email= dataSnapshot.getKey();
                                                email = email.replace("_",".");
                                                boolean online= ((Boolean) dataSnapshot.getValue()).booleanValue();
                                                User user = new User();
                                                user.setEmail(email);
                                                user.setOnline(online);
                                                post(type,user);
                                        }


                                Dentro de post, metodo privado
                                    //creo este metdo con lo siguiente y pilas tener un eventBus, y en el constructor lalmar a eventBus=GreenRobotEventBus.getInstance
                                    ContactListEvent event=new ContactListEvent
                                    event.setEventType(type)
                                    event.setUser(user)
                                    eventBus.post(event)
                                    //Con esto ya tengo listo cuando estoy agregando un child para la subscripcion

                                onChildChanged, cuando se cambia, el estatus,
                                    //para esto siempre voy hacer lo mismo, por lo que handleContact(), y necesito usar POO para hacerlo
                                    handleContact(dataSnapshot,Tipo de Evento(ContactListEvent.onContactChanged))

                                onChildRemoved, cuando se borra
                                    handleContact(dataSnapshot,Tipo de Evento(ContactListEvent.onContactChanged))

                                onChilMoved, cuando se mueve de una rama a otra)No la uso)
                                onCancelled, cuando se cancela la requisicion(No la uso)
                        }
                        Creo el metodo handleContact(DataSnapshot dataSnapshot,int type)

                            y pongo todo lo que use antes



                 En UnSubscribeToContactListEvent
                    Vamos a revisar que sea diferente de null, y luego,
                        helper.getMyContactsREference.removeEvListener(contactEventListener)
                        getMyContactrefrence porque estamos hablando del listado de contactos correspondientes a mi usuario, y le envio contactEventListener
                 En changeConnectionStatus



            En firebaseHelper




Pruebas y Repositorio, Video 10

    Con esto podemos regresar a la actividad, ContactListActivity y descomentar todo o que estaba comentado del presentador
        en Oncreate
            Desocmentamos Presenters. en instanciamos el presentador
                presenter=new ContactListPResenterImpl(this)
        setupAdapter
            cambiamos a un arraylistVacio


        //vamos a usar el adaptador, el presentador llama a metodos de la vista, onContactAdd,...
        onContactAdd(User user)
            adaptador.add(user)
        onContactChanged
            adaptador.update(user)
        onContactRemoved
            adaptador.remove(user)

        Creamos los metodos dentro del ContactLIstAdapter, (adapater)

        en add(user user)
            revisaremos si ya esta agregado y sino agregarlo
            si la lista contiene el usuario, y si no lo contiene lo agregamos, y luego lo actualizamos de una
         public void add(User user) {
        if (!contactList.contains(user)){
            contactList.add(user);
            notifyDataSetChanged();
        }
    }
        en update(user user)
            revisaremos si esta, actualizarlo
            para esto la lista necesita un set, que necestia la posicion, por lo que declaramos la posicion con indexOf,  notemos que aca se usa el equals, por eso lo implementamos
             public void update(User user) {
                    if (contactList.contains(user)){
                        int index = contactList.indexOf(user);
                        contactList.set(index,user);
                        notifyDataSetChanged();

                    }
            }
        en Remove(user User
            solo lo borraremos, pero lo eeliminamos directamoente, con remove
        public void remove(User user) {
        if (contactList.contains(user)){
            contactList.remove(user);
            notifyDataSetChanged();
            }
        }

    Para hacer esto, nos apoyaremos en el bjteto de la actividad que yo ya definí (USER)
        y agregamso un método equals, lo cual busca es comparar en el caso del usuario, mi parámetro de comparacion va a ser el Email y con este voy a asegurarme que un usuario sea igual a otro
            @Override
            public boolean equals(Object obj) {
                boolean equal = false;
                if (obj instanceof User){
                    User user= (User)obj;
                    equal = this.email.equals(user.getEmail());
                }

                return equal;
            }



    En ContactListActivity
            Para asegurarnis que al hacer click esta haciendo algo le mandamos un toast

                en onItemClick
                    Mandamos un Toast
                en onItemLongClick//para eliminar a un contacto
                    presenter.removeContact(user.getEmail)

            Ahora vamos a agregar el menu, y editaremos esta acatividad un poco
                sobreccargamos OnCreateOptionsMenu y onOptionsItemSelected
                En onCreateOptionsMenu(Menu menu)
                    getMenuInflater().inflate(R.menu.menu_contact_list,menu)
                        Creamos en la parte de recursos, una nueva carpeta, que se llame menu, y sobre esta una que se llame menu_contact_list,
                            Unicamente tengo un item que usa el string de cerrar sesion
                            <item android:id="@id/actionlogout" android:title="@string/...
                            android:orderInCategory="100"/>

                En optiosItemSelected(MenuItem item)
                    cuando este menu es llamado entocnes hago algo.
                            la forma para hacer esto(logout) vamos a limpiar las banderas, e inicializar la actividad del login, de esta manera cuando haga back no voy a tner problema en regresar a lo que estaba viendo actualmente, y previo a esto hago un presenter.signOff y en la actividad tengo que corregir que cuando vuelva al login no tenga historia de tal forma que cuando le de back no regrese a ver esta
                            if (item.getItemId() == R.id.action_logout){
                                        presenter.signOff();
                                        Intent intent = new Intent(this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }

                            <activity android:name=".intro.IntroActivity" android:noHistory="true">

    Debemos tener en cuenta que usamos en console.firebase, el nodo contacts y ponermos nuestro email





Para ADD CONTACT
        AddContact layout ViDEO1

            Creamos Fragment, AddContactFragment con su layout
            y dentro de layout creamos
            un EditText, que es el email
            un ProgressBar, ttien visibilidad gone y la idea de este es que cuandoestoy haciendo el proceso de agregar el contacto, me lova a mostrar y luego se va a cerrar y va a decir si se pudo o no se pudo agregar el contacto


    AddContact Estructura MVP, DialogFragment
        Para la Vista
            AddContactView(interfaz)
                Aqui exponemos los métodos de la vista
                    showImput,un input que quiero mostrar u oculatr dependiendo que este pasando
                    hideInput,

                    showProgress(), progressBar que quiero mostrar o ocualtar
                    hideProgress()

                    contactAdded(), para reportar que se agrego el contacto
                    contactNotAdded(), para reportar que hubo un error y no se pudo agregar el contacot

        Para el presentador

            AddContactPresenter(Interfaz)
                Aquilos métodos del presentador
                    onShow(), son mas o menos lo mismo del onCraete u onDestroy que hemos estadomanejando en las actividades, o fragmetes
                    onDestroy(), por lo que usamos esto para mostrar y destruir el dialgo

                    addContact(email), para agregar un contacto por meido del mail
                    onEventMainThread(AddContactEvent evnet), para reportar que ocurrió algún evento y que tengo que hacer alguna acción

        Una vez ya hecho el presentador procedemos a tener el Interactor
            AddContactInteractor
                En este únicamente conecto el presenaador con lo que va a ser la lógica de negocios
                 void execute(String email)

        Luego vamos a tener un Repository por lo que usamos
            AddContactRepository
                en donde tendremos
                addContact(String emial),

    Para ordenar tenemos evetnos , los evetnso que haran uso EventBus y en
    ui, el Fragmento o Actividad, y la interfaz de la vista


En el Fragemento AddContactFragment
    hacemos que implmente la vista y hereda de DiaglogFragment

        Dentro del onCreateView, creamos el Alert Diaglog, con getActivity como contexto, le configuro la vista asociada setView, y por ultimo la creo, la idea de sto separado es que
            hacemos un AlertDialog.Builder builder=nwe AlerDialog.Builer(getActitivy())
            builder.setView(vview(
            AlertDialg diaglo=builder.cretae()
        y retorno el diaglo

        Cambiamos onCreaetView, por onCreateDialog y devuelve un DIalog
            el View, lo obtenermos de la acitvidad, con getActivyt(),getLayoutInflater y container le mando null


        Dentro de los métodos de la vista pues haremos

            showInput(Vista)
                editTextEmail.setVisitbity(View.Visible)

            hideInput(Vista)
                editTextEmail.setVisitbity(View.GOne)
            showProgress(Vista
                progressBar.setVisitbylye(View.Visible)
            hideProgress(Vista)
                prgressBar.setVisity(View.GONE)


            oncontactAdded
                Toast. con getActivty su string. siguiendo el patron, package.tipoString(mensaje).descripcion(title)

            contactNotAdded
                mando el texto vacio
                y le mando el error con setError al editTxtEmail
            onDestroyView
                pilas para evitar el memorylick
                    super.ondestroy(view)
                    ButterKnife.unbind(this)

        Para hacer la prueba vamos a la actividad
            ContactlistActivity
                addContact(), del metodo del boton creado
                    new AddContactFragment(),show(getSupportFragmentManaget(),getAtring(R.string.addContactMessagetitle)
                        llamo a show y le envio el supportFragmenteManager, y el titylo del string resource

Video 3
        AddContact Construir funcionalidad del diálogo

            En AddCOntactFragment
                En onCreateDialog
                    Vamos a construir el buidler antes de hacer la vista que corresponde
                    AlerDiaglog.BUilder builder=new ALertDialog.BUilder.(getActity())
                        .setTitle(R.string.addcontactmessajetite)
                        .setPositiveButton(R.string.addcontactmessgeadd,new DialogInterface

                        .setNegativeButton(R.string.addcontactmessagecancel,new DialogInterface.OnClickListener(){
                            @Override
                                pubklioc void onClick(){

                                }
                        }

                Ahora luego del
                vista
                binding
                y le ponemos el
            dialog.setOnShowListener(this) y con esto implmenetamos dialogInterface.OnShowListner

            Con esto vamos a implementar a onShow
            En onShow
                Ciertas acciones asociadas, con esto la idea es que voy a poder controlar cuando el dialogo se cierra y cuando no, de tal forma que si ocurre un error no se cierre, la forma de usarlo es con onShowListener de lo contrario el dialogo se va a cerrar

                Por el momento no se hace nada dentro de los botones que estan en el dialogo, el momento que presiono algún botón se me cierra la pantalla para evitar esto editaremos el método onSHow
                    final AlerDialog, a partir del getDialog, casteandolo correctamente
                    final AlertDialog dialog = (AlertDialog) getDialog();

                Con esto vamos a Colocar un botón para positivo y uno para negativo y a cada uno de ellos le asociamos una acción
                y lo manejamos como botones normales
                            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

                        positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    addContactPresenter.addContact(inputEmail.getText().toString());
                                }
                        });

                        negativeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dismiss();
                                }
                        });

                    Como vimos en el positivo hacemos la acción, en este caso es el presentador, con lo que usamos el presentador.addContact y le mandamos el mail
                    y si acaso no quiero agregar un contacto solo hago dismiss()

                    Por último voy a validar que se mande a validar el onSHow del presntador, es decir que cuando el dialogo se muestra mando a llamar al onShow del presentador
                    presenter.onShow(), aguera de la validación de los botones


                En onDestroy(Fragment)
                    llamamos al presentador.onDestroy()
                    super.onDestroy();

                AddContactFragment(){
                    En este constructor, inicializamos el fragment,
                    presenter=new AddContactPresenetrImpl(this), por lo que necesito la vista para usarla, y com parametro la vista

                }


                Con esto al Cancelar se destruye, y al aceptar no se cierra el dialogo
                    En el evento del contacto agregado, agergamos un dismiss(), pero vemos que no se cierra, ya que esta en espera de el evento(contactAdded), y cuando se envie este evento se va a cerrar el dialogo.




Video4 AddContact Agregar Contacto.

    En AddContactPresenterImpl
        Ademas de la vista AddContactView, ncesitamos una instancia de EventBus eventBus, y una instancia de interactuador, AddContactInteractor, interactor,

        En el consttructor
            La añadimos con AddContactView, y además vamos a traer una instancia de Event Bus por greenRobot.getInstance(), y vamos a inicializar el interactuador,
            eventBus=GreenRobot.getInstance()
            interactor=new AddContactImpl();

        En onShow
            registramos al presentador como un elemento que esta escuchando
            eventBus.register(this)
        En onDestroy
            deRegistrar al presentador y ademas volver la vista nula
                view=null
                eventBus.unregister(this()
        En addContact
            Este va a llamar al interactuador,y le manda el email pero además va a revisar si la vista existe, le decimos que esconda primero el input  y luego que muestre el progressBar, antes de llamar el interactuador
                if(view!=null)
                    view.hideInput
                    view.showProgress

                interactor.execute(email)

        En onEventmainThread,
            Al recibir el evento voy a tener un posible error pero no voy a detallar el error, por loq ue vamos a hacer es ir a AddContactEvent y voy a ver si tengo error o no
            Revisamos si mi vista es diferente de null, (que la vista exista)
            , con lo que ocualtamos el progressbar y mostrar los inpus,
            Validamos si el evento incluye un error, entonces disparamos cierto evento de la vista de error con contactNotAdd, si no contactAddSuccess

                if (addContactView != null) {
                            addContactView.hideProgress();
                            addContactView.showInput();

                            if (event.isError()) {
                                addContactView.contactNotAdded();
                            } else {
                                addContactView.contactAdded();
                            }
                    }
            HASTA AQUÍ TENGO LISTO MI PRESENTADOR


        AddContactRepositoryImpl
            necesito una instancia de EventBus, otro de Firegbase Helper,

            En Constructor()
                this.evenyBus=FirebaseHelper.getInstance()
                helper=GreenRobot.getInstance()

            En addContact(String email)
                en este como este email , incluye un punto y Firebase no guarda ".", lo vamos a reemplazar,
                Obtengo la referencia del usuario, en base al email que tengo,
                luego uso esta referncia y le añado un addListenerForSingleValueEvent(new ValueEventListner)..., esto es IMPORTANTE, ya que va a ser un solo evento el que me interesa, creo que como solo tenemos un valor que me dice que es error o no,m entocnes es un solo evento, REVISAR ESTA PARTE, con los otros EventMainThread

                En onDataChanged,
                    Voy a traer primero el usuario a partir del Snapshot, con lo que voy a permitir que Firebase me ayude y llene todos los campos necesarios,
                      User user = snapshot.getValue(User.class);
                    Con esto voy a ver si este usuario es diferente de null,  ¡(Existe), se hace algo y si no vamos a emitir un error, Si acaso existe vamos a revisar si el usuario esta online, y voy a obtener mi referencia de contactos, entonces con esto nos corresponde colocar los valores
                    sobre myContactRefernce.child(key) que el key es el mail reemplazado los puntos, por _
                    Luego le hacemos .setValue(user.inOnline()), dependiendo si el usuario se encuentra onLine o no, con esto estoy agregando a mi referncia de usuarios al otro usuario,


                    Ahora me toca hacerlo a la inversa, porque lo estoy trabajando cruzado

                    String currentUserKey-helper.getAuhtUserEmail()

                    y reemplazo currentUserKey de los puntos por el guion bajo, con esto nos permite usar el email en múltiples lugares de Firebase

                    Ahora necestio un ReverseContacReference a través de contact references con el email que recibi el email del usuario, por utlimo sobre este reverse, hacemos un chils
                    y uso mi email le mando en setValue(User.ISONLINE), Le digo que yo estoy online y emito el evento que no tuve error
                     diciendole postSucess(),




                En onCancelled, podemos postear un evento como postError
                En post(boolean error)
                    aqui cramos un AddContactEvent event, y le mandamos el error como setErrorTrue.
                    o podemos usar un solo metodo para ver si tiene o no error
                    event new AddContactEvent
                    event.setError(boleano)
                    eventBus.post(event)










    En AddContactEvent
        necesito un bolleano que me dice si tengo error o no, y lo inicializamos como falso para que sea mucho mas facil cuando no haya error
        y genero sus gettters y setters


    En AddContactInteractorImpl,
        implmentamos de AddContactInteractor, y creamos un AddContactRepository, y dentro de execute(addContact), llamamos al repositorio con el parámetro que tenemos

        En el constructor por defecto instanciamos, y lo cramos por el momento lo dejamos vacío solo con los métodos por defecto

            repository=new AddContactRepositoryImpl()





Con esto podemos ver que si funciona al agregar un contacto , probar con un verdadero y con uno no valido,
Dentro del nodo de contactos del usuario que acabo de agregar, aparezco yo como online y dentro de mi nodo el aparece como OFFLINE
LA IDEA AQUÍ ES QUE TENGAN LOS DATOS CRUZADOS (BOOLEAN)

NOTA.REVISAR QEU CUANDO INGRESEMOS EL MAIL, ESTE NO NOS DEJE INGRESAR DATOS VACÍOS





CHAT CONFIGURACION
Video 1 Chat, LYOUT

        Chatlayout
            Es importatne que como vamos a tener un toolbar esta actividad al igual que contactos darle un noActionBar, que es un estilo específico para este tema


        En el layout
            Cramos un coordinatorLayout, y dentro del toolbar vamos a colocar el contenido dque represntaba cada una de las filas del listado, lo agregamos


Video2

    MVP
    Creamos la estructura MVP lo primero con la vista,

    Vista
        ChatView,
            void onMessageReceived(chatMessage msg), pues tenemos el chat como un pojo, lo cramos en entidades

    Presntador
        ChatPresenter
            Metodos para trabajar el registro y deregistro del listener del chat

                void onPause, cuando la vista se detiene, ya sea porque se cambio de activdad,
                void onResume, cuando la vista se muestra, solo cuando se muestra la vista se llama este métdo
            Metodos para trabajar con EventBus
                void onCreate
                void onDestroy, para evitar el memory lick de la vista

            Metdos de los mensages
                void  sendMessage(Sting msh), que recibe el mensaje
                void onEVentMainTHread(ChatEvent event), que recibe los eventosque se dan durante el chat

            MEtodo del mensaje
                Como todos los mensajes van a ir hacia un mismo destinatario
                void setChatRecipient(String recicpient), para configurarlo en el momento en que configuro el presentador

    Modelo
        Luego necestio dos interactuadores, uno para la sesison, y otro para los mensajes

            ChatSessionInteractor,
                changeConnectionStatus (booblena(online)), me sirve para el momento que cambio de pantalla, para modifivar el estado de mi conexion

            ChatInteractor
                sendMessage(Sting msg),
                setREcicpient(Sttign recipient), como tengo un solo tipo de recipient lo coloco de esta manera

                Todos estos vinculados al listner
                    subscribe
                    unsubscribe
                    destroyListener

        Repositorio
            Necesito implmentar métodos de ambos interactuadores,
                Metodos de ChatSessionINteractor, que al final solo usa el helper
                Metodos de ChatInteractor,

    TEngo toda la estructura creada ahora toca crear las clases concretas (Class InterfaceImpl), hasta ahora he trabajado abstracciones(Interface SomeName)




Video3, Vista Toolbar-
    Creamos las instancias de ChatActivity, y usamos el generador de butterknife en la actividad,
    Entonces una vez generado, esto voy a tener la data de algún lado(la actividad donde está el listado), entonces vamos a usar un intent para enviarlo y tenemos que enviar un par de contactos para ellos
        EmailKey, OnlineKey

    En ContactListActivity
        En esta actividda tenemos el metodo

        En onItemCLick
            Vamos a declarar lo que necesito mandarle a mi toolbar de la otra actividad
            Declaramos un intent nuevo a partir de la actividiad(thismChatActivity.class)
            intent.putExtra. como key a Email y online , y obtenermos del usuario, el mail y el estado correspondiente
            iniciamos la actividad a partir de este intent

                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra(ChatActivity.EMAIL_KEY,user.getEmail());
                    intent.putExtra(ChatActivity.ONLINE_KEY,user.isOnline());
                    startActivity(intent);

    En ChatActivity
        Volvemos a chatActity, todavia no estamos haciendo nada pero nos interesa mostrar el contenido del toolbar,
        Declaramos el ChatPresenter
        Declaramos el ChatAdapter(recyclerView), este se agrega cuando se recibe un mensage,
            Realizamos el chatPresenterImpl en el onCreate enviandole como parámetro la vista, (this) notemos que debe herdar de ChatView.

        En onMessageReceived
            Colocamos adapter.add(msg)
            //podemos hacer que el recyclerview se mueva hasta la parte inferiro donde está este mensage, mandandole la posicion que necesito
                adapter.add(msg);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        //Metodos del ciclo de vida de la actividad, con la l'ogica del presentador


        En onCreate()
            presenter.onCreaet()
        En onResume()
            presenter.onResume()
        En onPause()
            presneter.onPause()
        En on Destroy()
            presenter.onDestory()

        Adicional a esto hacemos un método para el recyclerview, llamados desde onCreate
            setupRecyclerView,
            setupAdapter
            setupToolbar
        El orden qeu ebemos respetar, es el siguiente y va a tener que estar configurado antes del toolbar

                    chatPresenter = new ChatPresenterImpl(this);
                    chatPresenter.onCreate();

                    setSupportActionBar(toolbar);
                    Intent intent = getIntent();
                    setToolbarData(intent);

                    setupAdapter();
                    setupRecyclerView();

        En setupToolbar(Intent i),
            Recibimos como parámetro getIntent()
            Vamos a obtener los datos a partir del intent, necesito la data de algun lado y por eso decalramos en la actividad anterior

                String recipient = i.getStringExtra(EMAIL_KEY);
                chatPresenter.setChatRecipient(recipient);
                boolean online = i.getBooleanExtra(ONLINE_KEY, false);
                String status = online ? "online" : "offline";
                int color = online ? Color.GREEN : Color.RED;

                txtUser.setText(recipient);
                txtStatus.setText(status);
                txtStatus.setTextColor(color);

            boolean online=i.getBooleanExtra(ONLINE_KEY,false), me retorna false si no lo recibo, por defecto es falso
            en base a esto voy a llenar los datos del toolbar. notemos que es lo mismo de contactListAdapter, en onBindViewHolder. y creamos todo
            sin holder,pero necesito un ImageLoaer que sea igual una instancia de GlideImageLoader, con su respectivo contexto

        En sertupRecyclerView
            Va a asignar al recyblerview un layoutManager, y este va a ser un linear LayoutMangetr qeu reciba la actividad como parámetro




    En ChatAdapter
        Creamos el adaptador que exitiende RecyclerView.Adapater<ChatAdapter.ViewHolder>, y viewHolder qu es una clase dentro de CHatAdapter, est'atica, debemos ponerla que herede de REcyclerView.ViewHolder y le creamos el constructor que me manda directamente







        NOTEMOS QUE SIMPRE QUE SE CREA UN PRESNTERIMPL, RECIBEN COMO PARÁMETRO UNA VISTA

        OTRA NOTA.
            GlideImageLoader.getInstance()==== ImageLoader img=new GlideImageLoader(getAplicationContext);
               Es una instancia a mano porque no tengo un singleton dentro de Glide








ChatAdapter video4

    Comenzamos a definir el POJO chatMessage,
        string msg, para el mensaje
        string sender, para ver quien env'ia
        boolean sendByMe, para ver si es enviado por mi o no

    Constructor vacío, necesario para que FIerbase no nos de un problema a la hora de construir el mensaje, creamos los Gttery setters correspondientes

    IMPORTANTE:
    Como SendByMe no es algo que firebase va a guardar le vamos a colocar algo de JsonIgnore, esta notación es para que el parser(Jackson) lo ignore le digo qeu esa propiedad no me interesa
        @JsonIgnoreproperties({"nombredelatributo"}), pero en las nuevas versinoes podemos hacer uso de Firebase que nos ayuda con este metodo de  @Exclude sobre el m;etodo que queremos excluir



    en chat, package
        Creamos los paquetes correspondietes
            ui, actividad y la vista
            adapters, adpatadores del recyclerView


        En ChatAdapter
            En este adapatador declaramos un contexto, este nos va servir para cambiar de color, eventualmente para obtener el color del tema
            private context contex

            Y un listado de ChatMessage
                List;ChatMessage chatMessages;

            En onCreateViewHolder
                vamos a inflar, podemos ir a contactlist, y copiarlo pero el layout cambia a contentChat

            En onBindViewHolder(*)
                Obteine un chatMessage a partir del listado utilizando la posicion como parámtro,
                y revisamos si ya se encuentra en el adaptador

                ChatMessgae chateMessage=getpositio..

                Extraemos solo el mensaje obtenido
                    String msg=chatMessge.getMsg()

                    holder.txtMessge.settText(msg),
                Abhora el color puede ser el color primario o el color accent, y le defino la gravedad par que si el mensage no es envíado por mí entonces este se mande a la derecha
                    int color= fetchColor(R.attr.colorPrimary)
                    int gravity=Gravity.LEFT

                preguntamos si el color es envidao por mi y cambiamos la llamada a color, y la gravedad
                    color= fetchColor(R.attr.colorAccent)
                    gravity=Gravity.RIGHT
                Asignmaos el color que hemos obtenido al mensaje
                    holder.txtMessage.setBackfroundColor(color)

                Luego vamos a asignarle al textMessge la gravedad pero para eso necesitamos un Linearlayout.LayoutParams obtenido a partir del holder, castiado a LInearlayout.layoutParams

                    LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)holder.txtMessage.getLayoutParams();
                Le mandamos la gravedad y luego le asignamos de vuelta con los parametros definidos

                    params.gravitty=gravity;
                    holder.txtMessage.setlayoutParams(params)




            En add
                Veo si ya esta el mensaje en el listado y si no está lo agergo y lo aviso al adapatador
                public void add(ChatMessage message) {
                        if (!alreadyInAdapter(message)) {
                            this.chatMessages.add(message);
                            this.notifyDataSetChanged();
                        }
                }


            En la clase de View Holder
                Configuro el txtMessage, y nada mas, con Butterknife y con esto ya puedo hacer el Binding.(onBindViewHOlder)

            en fetchColor(int color)
                Este método obtiene el color a partir del identificador que se le envía


    En ChatMessgae
                Implementamos el equals con todos los parametros del pojo, para saber si es igual
                voy a comparar el sender(en que envio), el mensaje, y además si es enviado por mi.

                @Override
                public boolean equals(Object obj) {
                        boolean equal = false;
                        if (obj instanceof ChatMessage){
                            ChatMessage msg= (ChatMessage)obj;
                            equal = this.sender.equals(msg.getSender())&& this.msg.equals(msg.getMsg())&&this.sentByMe == msg.sentByMe;

                        }

                        return equal;
                }

    En ChatActity

            En setupaAdapter
                Como ya definimos todo en el adaptador, creamos el adapter new ChatApdatper, y le envío un contexto con un listado nuevo vacío(Este se puede obtener desde una base de datos, o uno ya existente)
            En setupRecyclerView
                Le asigno al recycler view el adapatador que he creado

                recyclerView.setAdapter(adapter)


        Para pruebas creo dos mensajes dentro de setupAdapterm, mandandole todo lo que yo voy a definir

            ChatMessage msg1=new ChatMessage()
            ChatMessage msg2=new ChatMessage()

            msg1.setMsg("Hola como estas")
            msg2.setMsg("Aquí bien")

            msg1.setSendByMe(true)
            msg2.setSendByMe(false)

            adapter= new ChatAdapter(this,Array.asList(new ChatMessage[]{msg1,mg2}





ChatPresenetr y Event Video5

        En ChatPresenterImpl
            Vamos a trabajar en la implementacion del presentador, agregamos, el listener de ventso(EventBus), la vista, y los Interactuadores y procedmeos a inicializarlos de la manera que hemos definido como clases,
            EventBus eventBus;
            ChatView chatView;
            ChatInteractor chatInteractor;
            ChatSessionInteractor chatSessionInteractor;

                public ChatPresenterImpl(ChatView chatView){
                    this.chatView = chatView;
                    this.eventBus = GreenRobotEventBus.getInstance();

                    this.chatInteractor = new ChatInteractorImpl();
                    this.chatSessionInteractor = new ChatSessionInteractorImpl();
                }


            En onPause
                Al chat Interactor le va  adecir qeu se desuscriba y va a cambiar el estattus a trav'es dek sesion interactor con la constante de User.Online
                    chatInteractor.unSubscribeForChatUpates();
                    chatSessionInteractor.changeConnectionStatus(User.OFFLINE);
            En onResume
                Lo opuesto a onPause,
                    chatInteractor.subscribeForChatUpates();
                    chatSessionInteractor.changeConnectionStatus(User.ONLINE);
            en onCreate
                asignamos a event bus un registro del presentador, para que pueda escuchar por eventos
                    eventBus.register(this);
            en ONdestroy
                lo opuesto a onCrete, pero ademas destruimos al listener dentro del interactuador, y por ultimo hacemos nula la vista
                    eventBus.unregister(this);
                    chatInteractor.destroyChatListener();
                    chatView = null;

            en SetChatRecipient
                es parte del chatInteractor,
                        this.chatInteractor.setRecipient(recipient);

            en sendMessage
                es parte del CHatIntercctor
                        chatInteractor.sendMessage(msg);


            en onEventMain thread
                Pilas aqui tenemso que colocar que nos suscribimos y revisamos si la vista es difirente de null, y del evento vamos a recibir un CHatMessage, y vamos a decir view.onMessageReceived(event.getMessage(, como vemos es cuando tenenmos un nuevo mensaje se agregue al chat, es
                decir que en la vista le estamos dando a conocer que tenemos un mensaje y recibimos un mensaje porque un mesaje se va a mostrar, por eso no usamos valores que me dicen si tuve o no un error

                 @Override
                @Subscribe
                public void onEventMainThread(ChatEvent event) {
                    if (chatView != null) {
                            ChatMessage msg = event.getMessage();
                            chatView.onMessageReceived(msg);
                        }
                }




ChatRepository Video6

    Trabajando con los interactuadores, el repositorio deberia ser recibido en el constructor, pero para ver en que nos ayuda la inyecccion de independecias voy a hacer una instancia de este
        En ChatSessionInteractorImpl
            Necestimaos un repositorio y sobre este llaamos a los m;etodos con los que recibo
            en este caso en connectionStatus solo mandamos el metodo que tiene el repositorio. y su metodo cambiandole la sesion

            En el COnstructor, como vismo anteriormente
                Igualamos el repositorio a CHatRepositroyImpl(), que es el que dar'a la logica que necesitamos
            En ChangeBonnextionStatus
                Solo llamarmos al repository.onChangeConnection status mandandoel el boleano

        En ChatInteractorImpl
            Lo mismo que hicimos en la anterior, con ChatRepository
             EN el constructor, creamos la instancia del repositrio que hemos construido(notemso que lalmaamos al mismo)

            Llamamos a los metodos del repositorio que ya hemos definido con repository.nombreMetodo
            EN sendMessage
            En SetREcipient
            En SubsCribe
            En unSubrbe
            EN DestroyListener


        En ChatRepositoryImpl
            Aqui es donde se genra la logica,de tal manera que si cambian las liberria no se no shagan tan engorrosas los cambios

            Aqui vamos a tener un String de quien lo recibe(recipient) un FIrebaseHelper, y un CHildEventLIstner
            y tambien un EventBus Para enviar los eventos, y en el constructor enviamos algunos de estos

            En el constructor iniclizamos todos, pero sin recibir ninguno, como ya hemos venido haciendolo

                private String receiver;
                private FirebaseHelper helper;
                private ChildEventListener chatEventListener;

                public ChatRepositoryImpl(){
                    helper = FirebaseHelper.getInstance();
                    eventBus_GreenRobotEventBus.getInstance()
                }

            En setREcipient
                solo inicializamos el mail que hemos recibido con el que estamos reciendo, como un constructor mas o menos

            En ChangeConnectionStatus
                llamo solo al helper para que se encargue de esto que estamos haciendo
                helper.changeUserConnectionStatus(online)
            En estroyLIstener
                vuelvo nulo el eventListener
                chatEventLIstner=null

            En sendMessage
                lo que vamos ahcer es tomar el mail que est'a autenticado con helper.getAuthUserEmail
                luego Creamos un ChatMessageNuevo y mando como sender(el mailAutenticado), y el mensaje con el par'ametro que he recibido
                LUego tenemos un areferencia de firebase a traves del helper,,la referencia es de quien lo va a recibir*(recierver/recipient),  es importante recordar que va a obtener el correo del usuario autenticado  y el parametro que recibe para construirlo
                Luego chstaRefernce.push para que me genere un identificador firebase, y setvalue ChatMessage

                        String keySender = helper.getAuthUserEmail().replace(".","_");
                        ChatMessage chatMessage = new ChatMessage(keySender, msg);
                        DatabaseReference chatsReference = helper.getChatsReference(receiver);
                        chatsReference.push().setValue(chatMessage);

            En unSubsribe
                Valido si el istener es difreten de null hacemos la desubscripcion
                obtenemos todas las referencias del chat para el recipient(receiver), el que recibe, y remove eventListener y le envio el listner
                    if (chatEventListener != null) {
                            helper.getChatsReference(receiver).removeEventListener(chatEventListener);
                    }

            En SUbscribe
                De la misma forma lo hago para suscribirme, si acaso este chatEventListener es nulo entonces hago toda la logica para hacer este lintener
                    chatEventLitener=new ChatEventLisnter(){
                        Implmentamos los metodos pero por el meomemtno solo me interesa onCHildAddes, notemos qeu esta interfaz es de Fireabase
                        Por el momento me interesa cuadno un chat es enviado o se podira decir que onChildAdded
                            vamos a constuir un mensaje nuevo a partir del dataSnapshot.getValue y el Poojo del CHatMessage
                            Luego voy a verificar quien lo envia, a paritr del chatMessage, (obtengo el sender)

                            Para ver si yo fui el que envio el mensaje Obtengo el sender, y veo si este sender es igual al usuario actual autenticado entonces lo va a volver verdadero si no lo hace falso

                            ChatMessage.setSentByMy

                            Por utlimo voy a publicar un evento, entonces creamos un chatEvent nuevo, y todo lo que va a llevar es el mensaje
                            chatEvent.setMessage(chatEvent el mensaje que acabo de construir, y por ultimo posteo el evento
                            y para poder envair a EventBus, como el que va a estar escuchado, hago que se envia el mensaje constuido y luego los demas lo implmenten por ultimo posteamos el evento

                                            ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                                            String msgSender = chatMessage.getSender();
                                            msgSender = msgSender.replace("_",".");

                                            String currentUserEmail = helper.getAuthUserEmail();
                                            chatMessage.setSentByMe(msgSender.equals(currentUserEmail));

                                            ChatEvent chatEvent = new ChatEvent(chatMessage);
                                            eventBus.post(chatEvent);

    En ChatActivity
                LUego de qeu haga un click en sendMessage
                    presenter.sendMessage*(editText.getString.tostrint)
                    y le enviamos vacio para que quede vacion luego d que envaimos un mensaje

                En setuptollbar
                    setSupportActionBar(toolbar)


                    }
                Luego hago lo de anadir el chatREference (luego del if)
                    helper.getChatsReference(receiver).addCHildEventlistener(chatEventListener);


    Cuando envio un mensaje podemos ver que tenemso un guion bajo ,,separando con ___ ordenado alfabeticamente, teine el mensaje y quien lo envia, cada vez que hay un mensaje nuevo se agregan nodso con informacion de quien los envia y quien los recibe





EJERCICIOS

    Ej1, Crear la pantalla de SingUp en otra actividad poniendo todo lo que conlleva como MVP y Clean

        Layout
            Dentro de strings.xml
                Cambiamos valores  tanto para crear cuenta como para usarlo
                tambien el titulo de la aplicación

        En package singup
            ui
                SignUpActivity
                    el layout de este va a ser muy similar al de login,Creamos el titulo para que se mande el titulo a esta activity
                        setTitle(R.string....)


        En LoginActivity,
            handleSingUp cramos que nos lleve a la pantalla de singUpActivity









