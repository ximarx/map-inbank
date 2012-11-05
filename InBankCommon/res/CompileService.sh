# Questo script per funzionare deve essere eseguito
# dall'interno della cartella /InBankCommon/res
# Permette la compilazione di tutti i servizi necessari

cd ../bin
echo "Compilazione ServiceFactory"
rmic gccb.net.rmi.RMIServer.ServiceFactory
echo "Compilazione Servizio Bancomat"
rmic gccb.net.rmi.services.BancomatService
echo "Compilazione Servizio Cliente"
rmic gccb.net.rmi.services.ClienteService
echo "Compilazione Servizio Impiegato"
rmic gccb.net.rmi.services.ImpiegatoService
cd ../res


