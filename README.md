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
















