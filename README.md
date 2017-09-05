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



