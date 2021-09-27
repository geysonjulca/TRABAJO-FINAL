import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TrabajoFinal {

    public static void confirmacion_alternativas(boolean[] cliente_respuesta){
        int i=-1;
        int cont=0;
        do{
            i++;
            cont=i+1;
            if(cliente_respuesta[i]==false) System.out.println("Día "+cont+": No se tiene respuesta, enviar nuevamente el mensaje");
            else System.out.println("Día "+cont+": El cliente realizó la confirmación");
        } while((i<5)&(cliente_respuesta[i]==false));
    }

    public static void alternativas(){
        //Base de datos que el sistema maneja:
        String[] colores_escasos = {"ivory","mauve","fucsia","coral","azul aqua"};
        String[] colores_alternativos={"blanco", "rosa_bebe", "rosado","naranja","turquesa"};
        //Colores pedidos por el cliente
        String[] colores_cliente={"rojo","celeste","ivory","mauve"};

        int cont = 0;
        for (int i=0; i<colores_escasos.length;i++){
            for (int j=0; j<colores_cliente.length;j++){
                if (colores_cliente[j].equals(colores_escasos[i])) cont=cont+1;
                }
            }

        //Se crea el arreglo para los colores que son escasos, en el pedido del cliente
        String escasos_cliente[]=new String[cont];

        int k =0;

        for (int i=0; i<colores_escasos.length;i++){
            for (int j=0; j<colores_cliente.length;j++){
                if (colores_cliente[j].equals(colores_escasos[i])) {
                    escasos_cliente[k]=colores_cliente[j];
                    k++;
                }
            }
        }

        if (escasos_cliente.length>0){
            System.out.print("En el pedido existen "+cont+" colores de tela escasa: ");
            for(int i=0;i< escasos_cliente.length;i++){
                System.out.print(escasos_cliente[i]+" ");
            }
            System.out.println("");
            System.out.println("Estas telas pueden ser remplazadas por los siguientes colores respectivamente: ");

            for(int i=0;i< colores_escasos.length;i++){
                for(int j=0;j<escasos_cliente.length;j++){
                    if(escasos_cliente[j].equals(colores_escasos[i]))
                        System.out.print(colores_alternativos[i]+" ");
                }
            }
            System.out.println("enviar correo de confirmación");

            //Respuesta del a travez de los días
            boolean[] cliente_respuesta={false,false,true,false,false};

            confirmacion_alternativas(cliente_respuesta);

        }
        else {
            System.out.print("No hay ninguna tela escasa, se puede proseguir sin dificultad");
            System.out.println(" enviar correo de aceptación de orden y comprar las telas");
        }

    }

    public static int envio(String cliente){
        int dias=0;
        if (cliente.equals("Tottus Chile")) dias=30;
        else if (cliente.equals("Livly")) dias=60;
        else dias=1;

        return dias;
    }

    public static double monto(int dias_retraso,String cliente){
        double monto = 0;
        if (cliente.equals("Tottus Chile")){
            for(int i=1;i<=7;i++){
                monto=monto+3+2*(i-1);
            }
            while(dias_retraso>7){
                monto=monto+15;
                dias_retraso--;
            }
        }
        else if (cliente.equals("Livly")){
            for(int i=1;i<=7;i++){
                monto=monto+5*i;
            }
            while(dias_retraso>7){
                monto=monto+35;
                dias_retraso--;
            }
        }
        else {
            for(int i=1;i<=7;i++){
                monto=monto+i;
            }
            while(dias_retraso>7){
                monto=monto+7;
                dias_retraso--;
            }
        }
        return monto;
    }

    public static void main (String[]arg){
        System.out.println("Seguimieto a la orden de compra:");
        alternativas();

        System.out.println("Seguimiento a las fechas de entrega");
        String cliente = "Tottus Chile";
        LocalDate fecha_entrega_programada = LocalDate.of(2021,11,30);
        int dias_envio = envio(cliente); // Días antes que se tiene que tener el pedido
        int dias_telas = 30; // Las telas siempre deben tenerse 30 días antes de la fecha de envío
        LocalDate fecha_envío_programado=fecha_entrega_programada.minusDays(dias_envio);
        LocalDate fecha_telas_programada=fecha_envío_programado.minusDays(dias_telas);

        System.out.println("La fecha de entrega programada de llegada  de las telas tiene que ser: "+fecha_telas_programada);
        System.out.println("La fecha programada para finalizar la producción y enviar la mercadería es: "+fecha_envío_programado);


        LocalDate fecha_telas_real= LocalDate.of(2021,10,11);
        LocalDate fecha_envío_real= LocalDate.of(2021,11,20);

        int dias_retraso_telas =(int) ChronoUnit.DAYS.between(fecha_telas_programada,fecha_telas_real);
        int dias_retraso_envio= (int) ChronoUnit.DAYS.between(fecha_envío_programado,fecha_envío_real);

        double monto_telas= monto(dias_retraso_telas,cliente);
        double monto_produccion=monto(dias_retraso_envio,cliente);

        if (dias_retraso_telas<=0) System.out.println("No existe un retraso de parte del proveedor");
        else System.out.println("El monto a pagar por el retraso de "+dias_retraso_telas+" es: $ "+monto_telas);

        int dias_retraso_neto=0;
        double monto_neto=0;
        if (dias_retraso_envio<=0) System.out.println("No existe un retraso de parte de producción");
        else
            if (monto_telas>=monto_produccion) System.out.println("No existe un retraso de parte de producción, el retraso se dio en la entrega de telas");
            else {
                dias_retraso_neto=dias_retraso_envio-dias_retraso_telas;
                monto_neto = monto_produccion - monto_telas;
                System.out.println("El monto a pagar por el retraso de " + dias_retraso_neto + " es: $ " + monto_neto);
            }
    }

}
