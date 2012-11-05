/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Data for map_agenti
INSERT INTO map_agenti VALUES ('9', '1', 'BANCOMAT');
INSERT INTO map_agenti VALUES ('4', '1', 'CLIENTE');
INSERT INTO map_agenti VALUES ('1', '1', 'IMPIEGATO');
INSERT INTO map_agenti VALUES ('10', '2', 'BANCOMAT');
INSERT INTO map_agenti VALUES ('5', '2', 'CLIENTE');
INSERT INTO map_agenti VALUES ('2', '2', 'IMPIEGATO');
INSERT INTO map_agenti VALUES ('11', '3', 'BANCOMAT');
INSERT INTO map_agenti VALUES ('6', '3', 'CLIENTE');
INSERT INTO map_agenti VALUES ('3', '3', 'IMPIEGATO');
INSERT INTO map_agenti VALUES ('12', '4', 'BANCOMAT');
INSERT INTO map_agenti VALUES ('7', '4', 'CLIENTE');
INSERT INTO map_agenti VALUES ('13', '5', 'BANCOMAT');
INSERT INTO map_agenti VALUES ('8', '5', 'CLIENTE');
INSERT INTO map_agenti VALUES ('14', '6', 'BANCOMAT');


-- Data for map_bancomat
INSERT INTO map_bancomat VALUES ('1', '2', 'password', '250.0');
INSERT INTO map_bancomat VALUES ('2', '2', 'password', '5000.0');
INSERT INTO map_bancomat VALUES ('3', '2', 'password', '100.0');
INSERT INTO map_bancomat VALUES ('4', '4', 'password', '100.0');
INSERT INTO map_bancomat VALUES ('5', '4', 'password', '100.0');
INSERT INTO map_bancomat VALUES ('6', '3', 'password', '100.0');


-- Data for map_conti
INSERT INTO map_conti VALUES ('1', '3', '20000.0', 'false');
INSERT INTO map_conti VALUES ('2', '3', '90000.0', 'false');
INSERT INTO map_conti VALUES ('3', '1', '250000.0', 'false');
INSERT INTO map_conti VALUES ('4', '3', '100000.0', 'false');
INSERT INTO map_conti VALUES ('5', '2', '100.0', 'false');


-- Data for map_fidi
INSERT INTO map_fidi VALUES ('2', '1000.0');
INSERT INTO map_fidi VALUES ('4', '2500.0');
INSERT INTO map_fidi VALUES ('5', '3000.0');


-- Data for map_impiegati
INSERT INTO map_impiegati VALUES ('1', 'imp-1', 'password', 'Impiegato-1');
INSERT INTO map_impiegati VALUES ('2', 'imp-2', 'password', 'Impiegato-2');
INSERT INTO map_impiegati VALUES ('3', 'imp-3', 'password', 'Impiegato-3');


-- Data for map_persone
INSERT INTO map_persone VALUES ('1', 'Giancarlo', 'Rossi', 'Vicolo Corto 1');
INSERT INTO map_persone VALUES ('3', 'Nicola', 'Verdi', 'Piazza Umberto 183 scala 2B');
INSERT INTO map_persone VALUES ('2', 'Robero', 'Bianchi', 'Viale Magellano 23');


-- Data for map_pinconti
INSERT INTO map_pinconti VALUES ('1', 'password');
INSERT INTO map_pinconti VALUES ('2', 'password');
INSERT INTO map_pinconti VALUES ('3', 'password');
INSERT INTO map_pinconti VALUES ('4', 'password');
INSERT INTO map_pinconti VALUES ('5', 'password');


-- Data for map_transazioni
INSERT INTO map_transazioni VALUES ('1', '1', '20000.0', '2008-01-15', 'APERTURA_CONTO', '1');
INSERT INTO map_transazioni VALUES ('2', '2', '90000.0', '2008-01-15', 'APERTURA_CONTO', '1');
INSERT INTO map_transazioni VALUES ('3', '3', '250000.0', '2008-01-15', 'APERTURA_CONTO', '1');
INSERT INTO map_transazioni VALUES ('4', '4', '100000.0', '2008-01-15', 'APERTURA_CONTO', '1');
INSERT INTO map_transazioni VALUES ('5', '5', '100.0', '2008-01-15', 'APERTURA_CONTO', '1');
INSERT INTO map_transazioni VALUES ('6', '2', '0.0', '2008-02-15', 'ABILITAZIONE_BANCOMAT', '1');
INSERT INTO map_transazioni VALUES ('7', '2', '0.0', '2008-02-15', 'ABILITAZIONE_BANCOMAT', '1');
INSERT INTO map_transazioni VALUES ('8', '2', '0.0', '2008-02-15', 'ABILITAZIONE_BANCOMAT', '1');
INSERT INTO map_transazioni VALUES ('9', '4', '0.0', '2008-02-15', 'ABILITAZIONE_BANCOMAT', '1');
INSERT INTO map_transazioni VALUES ('10', '4', '0.0', '2008-02-15', 'ABILITAZIONE_BANCOMAT', '1');
INSERT INTO map_transazioni VALUES ('11', '3', '0.0', '2008-02-15', 'ABILITAZIONE_BANCOMAT', '1');


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
