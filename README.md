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

y al constructor le definmos como un EventBUs de green robot



Con esto me garantizo que cuand las cosas cambien de la liberria o que por alguna razon quiera otra, mi codigo cambia menormente
solo cambio la implementacion
