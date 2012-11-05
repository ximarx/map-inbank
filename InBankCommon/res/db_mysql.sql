-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-Debian_1ubuntu3-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

--
-- Definition of table `map`.`map_agenti`
--

DROP TABLE IF EXISTS `map_agenti`;
CREATE TABLE  `map_agenti` (
  `idAgente` int(11) NOT NULL auto_increment,
  `idSpecifico` int(11) NOT NULL,
  `tipoAgente` enum('BANCOMAT','CLIENTE','IMPIEGATO') NOT NULL,
  PRIMARY KEY  (`idAgente`),
  UNIQUE KEY `new_index` (`idSpecifico`,`tipoAgente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `map_persone`
--

DROP TABLE IF EXISTS `map_persone`;
CREATE TABLE  `map_persone` (
  `idPersona` int(11) NOT NULL auto_increment,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `indirizzo` varchar(255) NOT NULL,
  PRIMARY KEY  (`idPersona`),
  UNIQUE KEY `UNIQUE_nome_cognome_indirizzo` (`nome`,`cognome`,`indirizzo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Definition of table `map_conti`
--

DROP TABLE IF EXISTS `map_conti`;
CREATE TABLE  `map_conti` (
  `idConto` int(11) NOT NULL auto_increment,
  `idPersona` int(11) NOT NULL,
  `saldo` float NOT NULL default '0',
  `fChiuso` tinyint(1) NOT NULL,
  PRIMARY KEY  (`idConto`),
  KEY `new_fk_constraint` (`idPersona`),
  CONSTRAINT `new_fk_constraint` FOREIGN KEY (`idPersona`) REFERENCES `map_persone` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Definition of table `map_fidi`
--

DROP TABLE IF EXISTS `map_fidi`;
CREATE TABLE  `map_fidi` (
  `idConto` int(11) NOT NULL,
  `fido` float NOT NULL,
  PRIMARY KEY  (`idConto`),
  CONSTRAINT `FK_conti_fido` FOREIGN KEY (`idConto`) REFERENCES `map_conti` (`idConto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Definition of table `map_impiegati`
--

DROP TABLE IF EXISTS `map_impiegati`;
CREATE TABLE  `map_impiegati` (
  `idImpiegato` int(11) NOT NULL auto_increment,
  `username` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL COMMENT 'codifica md5',
  `visualizzato` varchar(50) NOT NULL,
  PRIMARY KEY  (`idImpiegato`),
  UNIQUE KEY `username_unica` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Definition of table `map_pinconti`
--

DROP TABLE IF EXISTS `map_pinconti`;
CREATE TABLE  `map_pinconti` (
  `idConto`int(11) NOT NULL,
  `password` varchar(32) NOT NULL COMMENT 'codificata md5',
  PRIMARY KEY  (`idConto`),
  CONSTRAINT `FK_codiceconto` FOREIGN KEY (`idConto`) REFERENCES `map_conti` (`idConto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `map_transazioni`
--

DROP TABLE IF EXISTS `map_transazioni`;
CREATE TABLE  `map_transazioni` (
  `idTransazione` int(11) NOT NULL auto_increment,
  `idConto` int(11) NOT NULL,
  `importo` float NOT NULL,
  `data` date NOT NULL,
  `tipoTransazione` ENUM('APERTURA_CONTO','CHIUSURA_CONTO','ADDEBITO','ACCREDITO','MODIFICA_FIDO','PRELIEVO','ABILITAZIONE_BANCOMAT', 'RIMOZIONE_BANCOMAT') NOT NULL,
  `idAgente` int(11) NOT NULL,
  PRIMARY KEY  (`idTransazione`),
  KEY `FK_transazione_conto` (`idConto`),
  CONSTRAINT `FK_transazione_conto` FOREIGN KEY (`idConto`) REFERENCES `map_conti` (`idConto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `map_bancomat`
--

DROP TABLE IF EXISTS `map_bancomat`;
CREATE TABLE `map_bancomat` (
  `idBancomat` INT(11)  NOT NULL AUTO_INCREMENT,
  `idConto` INT(11) NOT NULL,
  `codiceSegreto` VARCHAR(32)  NOT NULL,
  `massimoPrelevabile` FLOAT  NOT NULL DEFAULT 0,
  PRIMARY KEY(`idBancomat`),
  CONSTRAINT `FK_bancomat_conto` FOREIGN KEY `FK_bancomat_conto` (`idConto`)
    REFERENCES `map_conti` (`idConto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
ENGINE = InnoDB;


DROP PROCEDURE IF EXISTS `addTransazione`;

DELIMITER $$

CREATE PROCEDURE `addTransazione` (IDCONTO INT(11), IDAGENTE INT(11), IMPORTO FLOAT, DATA DATE, TIPOTRANSAZIONE VARCHAR(255))
BEGIN
	INSERT INTO `map_transazioni` (idConto, importo, data, tipoTransazione, idAgente) VALUES
	(IDCONTO, IMPORTO, DATA, TIPOTRANSAZIONE, IDAGENTE);
END$$

DELIMITER ;


-------------------------------------------------------------
-- NUOVE FUNZIONI
-------------------------


-----------
--- getSpecificoDaAgente
--- Ritorna l'idSpecifico da un IdAgente
-----------

DROP FUNCTION IF EXISTS `getSpecificoDaAgente`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `getSpecificoDaAgente`(IDAGENTE INT(11) ) RETURNS INT(11)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE IDSPECIFICO INT(11);
	SET IDSPECIFICO = (SELECT a.idSpecifico FROM `map_agenti` a WHERE a.idAgente = IDAGENTE);
	RETURN IDSPECIFICO;
END $$

DELIMITER ;


-----------
--- getBancomat
--- Ritorna informazioni bancomat da IDAGENTE
-----------


DROP PROCEDURE IF EXISTS `getBancomat`;

DELIMITER $$

CREATE PROCEDURE getBancomat(IDAGENTE INT(11))
BEGIN
	DECLARE IDBANCOMAT INT(11);
	SET IDBANCOMAT = (SELECT getSpecificoDaAgente(IDAGENTE));
	SELECT 
		b.idBancomat as idBancomat,
		b.idConto as idConto,
		b.massimoPrelevabile as massimoPrelevabile
	FROM `map_bancomat` b WHERE  b.idBancomat = IDBANCOMAT;
END$$

DELIMITER ;


---------------------
--- getConto
--- Ritorna tutte le informazioni relative al conto
-------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `getConto`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getConto`(IDCONTO INT(11) )
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE FIDO FLOAT;
	DECLARE ULTIMOMOV DATE;

	SET FIDO = (SELECT (f.fido + 0) as fido FROM `map_fidi` f WHERE f.IdConto = IDCONTO);
	IF FIDO IS NULL THEN
		SET FIDO = 0;
	END IF;

	SET ULTIMOMOV = (SELECT MAX(t.data) FROM `map_transazioni` t WHERE t.idConto = IDCONTO);

	SELECT
		c.idConto as idConto,
		c.idPersona as idPersona,
		p.nome as intestatarioNome,
		p.cognome as intestatarioCognome,
		p.indirizzo as intestatarioIndirizzo,
		c.saldo as saldo,
		FIDO as fido,
		ULTIMOMOV as ultimoMovimento
	FROM
		`map_conti` c JOIN (`map_persone` p) ON (c.idPersona = p.idPersona)
	WHERE
		c.idConto = IDCONTO;
		
END$$

DELIMITER ;



---------------------
--- getContoBancomat
--- Ritorna tutte le informazioni relative al conto
-------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `getContoBancomat`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getContoBancomat`(IDAGENTE INT(11) )
    READS SQL DATA
    DETERMINISTIC
BEGIN
	
	DECLARE IDBANCOMAT INT(11);
	DECLARE IDCONTO INT(11);

	SET IDBANCOMAT = (SELECT getSpecificoDaAgente(IDAGENTE));
	SET IDCONTO = (SELECT b.idConto FROM `map_bancomat`b WHERE b.idBancomat = IDBANCOMAT);

	CALL getConto(IDCONTO);
		
END$$

DELIMITER ;




--
-- Definition of function `checkAuthBancomat`
-- Fa controllo di autenticazione e ritorna l'idAgente associato al client se autenticazione ha successo, altrimenti -1
--


DROP FUNCTION IF EXISTS `checkAuthBancomat`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `checkAuthBancomat`(USER INT(11), PASSWD VARCHAR(32) ) RETURNS INT(11)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ID INT(11);
	SET ID = (SELECT idBancomat FROM `map_bancomat` p WHERE p.idBancomat = USER AND p.codiceSegreto = PASSWD);
	IF (ID) IS NOT NULL THEN
		RETURN (SELECT getAgenteDaSpecifico(ID, "BANCOMAT"));
	ELSE
		RETURN -1;
	END IF;
END $$

DELIMITER ;


---------------------------------------
--- doPrelievo
--- Esegue un prelievo da un contro, controllando che l'importo sia prelevabile e non superi
--- il massimo prelevabile dal bancomat



DROP FUNCTION IF EXISTS `doPrelievo`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `doPrelievo`(IDAGENTE INT(11), IMPORTO FLOAT) RETURNS tinyint(1)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE IDCONTO INT(11);
	DECLARE IDBANCOMAT INT(11);
	DECLARE MASSIMOPRELEVABILE FLOAT;
	DECLARE MASSIMOPRELEVABILECONTO FLOAT;

	SET IDBANCOMAT = (SELECT getSpecificoDaAgente(IDAGENTE));

	SET IDCONTO = (SELECT b.idConto FROM `map_bancomat`b WHERE b.idBancomat = IDBANCOMAT);
	SET MASSIMOPRELEVABILE = (SELECT b.massimoPrelevabile FROM `map_bancomat`b WHERE b.idBancomat = IDBANCOMAT);
	SET MASSIMOPRELEVABILECONTO = (SELECT massimoPrelevabileConto(IDCONTO));

	IF ( isChiuso(IDCONTO) IS FALSE ) AND 
		( existConto(IDCONTO) IS TRUE ) AND
		( MASSIMOPRELEVABILE >= IMPORTO) AND
		( MASSIMOPRELEVABILECONTO >= IMPORTO ) THEN
		 
		UPDATE map_conti c SET c.saldo = c.saldo - IMPORTO WHERE c.idConto = IDCONTO;
		CALL addTransazione(IDCONTO, IDAGENTE, IMPORTO, CURDATE(), "PRELIEVO");

		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$

DELIMITER ;



--
-- Definition of function `massimoPrelevabileConto`
-- Ritorna il valore del massimo prelevabile dal conto
--

DROP FUNCTION IF EXISTS `massimoPrelevabileConto`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `massimoPrelevabileConto`(IDCONTO INT(11)) RETURNS FLOAT
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE FIDO FLOAT;

	SET FIDO = (SELECT (f.fido + 0) as fido FROM `map_fidi` f WHERE f.idConto = IDCONTO);
	IF FIDO IS NULL THEN
		SET FIDO = 0;
	END IF;
	RETURN (SELECT ( c.saldo + FIDO ) as massimoPrelvabileConto FROM `map_conti` c WHERE c.idConto = IDCONTO);

END $$

DELIMITER ;




--
-- Definition of function `isChiuso`
-- controlla solo se e' chiuso, se non esiste ritorna false
--

DROP FUNCTION IF EXISTS `isChiuso`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `isChiuso`(IDCONTO INT(11)) RETURNS tinyint(1)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	IF ( SELECT fChiuso FROM map_conti c WHERE  c.idConto = IDCONTO AND fChiuso = TRUE) IS NOT NULL THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$

DELIMITER ;





--
-- Definition of function `existConto`
-- Controlla l'esistena di un conto (chiuso o no è indifferente
--

DROP FUNCTION IF EXISTS `existConto`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `existConto`(IDCONTO INT(11)) RETURNS tinyint(1)
    READS SQL DATA
BEGIN
	IF (SELECT idConto FROM map_conti c WHERE c.idConto = IDCONTO ) IS NOT NULL THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$

DELIMITER ;





--
-- Definition of function `doBonifico`
--

DROP PROCEDURE IF EXISTS `doBonifico`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE  `doBonifico`(IDCONTO_ORIGINE INT(11), IDCONTO_DESTINAZIONE INT(11), IMPORTO FLOAT, IDAGENTE INT(11))
BEGIN
	
	IF ( isChiuso(IDCONTO_ORIGINE) IS FALSE ) AND ( existConto(IDCONTO_ORIGINE) IS TRUE ) AND
		( isChiuso(IDCONTO_DESTINAZIONE) IS FALSE ) AND ( existConto(IDCONTO_DESTINAZIONE) IS TRUE ) THEN
		START TRANSACTION;
		IF ( doAddebito(IDCONTO_ORIGINE, IDAGENTE, IMPORTO) IS TRUE ) THEN
			IF ( doAccredito(IDCONTO_DESTINAZIONE, IDAGENTE, IMPORTO) IS TRUE ) THEN
				COMMIT;
				SELECT (TRUE) risultato;
			ELSE
				ROLLBACK;
				SELECT (FALSE) risultato;
			END IF;
		ELSE
			ROLLBACK;
			SELECT (FALSE) risultato;
		END IF;
	ELSE
		SELECT (FALSE) risultato;
	END IF;
END $$

DELIMITER ;


--
-- Definition of function `doAddebito`
-- Esegue un addebito controllando se possibile e ritorna esito
--

DROP FUNCTION IF EXISTS `doAddebito`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `doAddebito`(IDCONTO INT(11), IDAGENTE INT(11), IMPORTO FLOAT) RETURNS tinyint(1)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	IF ( isChiuso(IDCONTO) IS FALSE ) AND
		( existConto(IDCONTO) IS TRUE ) AND
		( massimoPrelevabileConto(IDCONTO) >= IMPORTO ) THEN
		UPDATE map_conti c SET c.saldo = c.saldo - IMPORTO WHERE c.idConto = IDCONTO;
		CALL addTransazione(IDCONTO, IDAGENTE, IMPORTO, CURDATE(), "ADDEBITO");
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$

DELIMITER ;



--
-- Definition of function `doAccredito`
--

DROP FUNCTION IF EXISTS `doAccredito`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `doAccredito`(IDCONTO INT(11), IDAGENTE INT(11), IMPORTO FLOAT) RETURNS tinyint(1)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	IF ( isChiuso(IDCONTO) IS FALSE ) AND ( existConto(IDCONTO) IS TRUE ) THEN
		UPDATE map_conti c SET c.saldo = c.saldo + IMPORTO WHERE c.idConto = IDCONTO;
		CALL addTransazione(IDCONTO, IDAGENTE, IMPORTO, CURDATE(), "ACCREDITO");
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$

DELIMITER ;





--
-- Definition of function `checkAuthCliente`
-- RITORNA IDAGENTE
--


DROP FUNCTION IF EXISTS `checkAuthCliente`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `checkAuthCliente`(USER INT(11), PASSWD VARCHAR(32) ) RETURNS INT(11)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ID INT(11);
	SET ID = (SELECT idConto FROM `map_pinconti` p WHERE p.idConto = USER AND p.password = PASSWD);
	IF (ID) IS NOT NULL THEN
		RETURN (SELECT getAgenteDaSpecifico(ID, "CLIENTE"));
	ELSE
		RETURN -1;
	END IF;
END $$

DELIMITER ;




--
-- Definition of function `getAgenteDaSpecifico`
-- RITORNA IDAGENTE
--



DROP FUNCTION IF EXISTS `getAgenteDaSpecifico`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `getAgenteDaSpecifico`(IDSPECIFICO INT(11), TIPO VARCHAR(255) ) RETURNS INT(11)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ID INT(11);
	SET ID = (SELECT idAgente FROM `map_agenti` a WHERE a.idSpecifico = IDSPECIFICO AND a.tipoAgente = TIPO);
	RETURN ID;
END $$

DELIMITER ;




--
-- Definition of function `addBancomat`
-- Aggiunte un nuovo bancomat ad un conto gia aperto. Ritorna l'id del conto o -1 se non creato
--

DROP FUNCTION IF EXISTS `addBancomat`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `addBancomat`(IDCONTO INT(11), IDAGENTE INT(11), CODICE VARCHAR(32), MASSIMO FLOAT ) RETURNS int(11)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ID INT;
	IF ( isChiuso(IDCONTO) IS FALSE ) AND (existConto(IDCONTO) IS TRUE) THEN
		INSERT INTO `map_bancomat` (idConto, codiceSegreto, massimoPrelevabile) VALUES
		(IDCONTO, CODICE, MASSIMO);
		SET ID = LAST_INSERT_ID();
		CALL addTransazione(IDCONTO, IDAGENTE, 0, CURDATE(), "ABILITAZIONE_BANCOMAT");
		INSERT INTO `map_agenti` (idSpecifico, tipoAgente) VALUES
		(ID, "BANCOMAT");
		RETURN ID;
	ELSE
		RETURN -1;
	END IF;
END $$

DELIMITER ;




--
-- Definition of function `checkAuthImpiegato`
-- RITORNA IDAGENTE
--


DROP FUNCTION IF EXISTS `checkAuthImpiegato`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `checkAuthImpiegato`(USER VARCHAR(255), PASSWD VARCHAR(32) ) RETURNS INT(11)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ID INT(11);
	SET ID = (SELECT idImpiegato FROM `map_impiegati` p WHERE p.`username` = USER AND p.`password` = PASSWD);
	IF (ID) IS NOT NULL THEN
		RETURN (SELECT getAgenteDaSpecifico(ID, "IMPIEGATO"));
	ELSE
		RETURN -1;
	END IF;
END $$

DELIMITER ;





--
-- Definition of function `rimuoviBancomat`
-- Rimuove un bancomat dalla lista annullando la sua chiave per login
--

DROP PROCEDURE IF EXISTS `rimuoviBancomat`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE  `rimuoviBancomat`(IDUNO INT(11), IDDUE INT(11), IDTRE INT(11) )
BEGIN
	DELETE FROM `map_bancomat` WHERE idBancomat = IDUNO AND idConto = IDDUE;
	DELETE FROM `map_agenti` WHERE idSpecifico = IDUNO AND tipoAgente = "BANCOMAT";
	CALL addTransazione(IDDUE, IDTRE, 0, CURDATE(), "RIMOZIONE_BANCOMAT");
END $$

DELIMITER ;






--
-- Definition of function `modificaFido`
-- Modifica il fido di un conto con fido
--

DROP PROCEDURE IF EXISTS `modificaFido`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE  `modificaFido`(IDCONTO INT(11), FIDO FLOAT, IDAGENTE INT(11) )
BEGIN
	IF ( isConFido(IDCONTO) IS TRUE ) THEN
		UPDATE `map_fidi` f SET f.fido = FIDO WHERE f.idConto = IDCONTO;
		CALL addTransazione(IDCONTO, IDAGENTE, FIDO, CURDATE(), "MODIFICA_FIDO");
	END IF;
END $$

DELIMITER ;





--
-- Definition of function `isConFido`
--

DROP FUNCTION IF EXISTS `isConFido`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `isConFido`(IDCONTO INT(11)) RETURNS tinyint(1)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	IF ( SELECT fido FROM map_fidi f WHERE  f.idConto = IDCONTO ) IS NOT NULL AND isChiuso(IDCONTO) IS FALSE THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$

DELIMITER ;




--
-- Definition of function `getUltimiMovimentiConto`
--

DROP PROCEDURE IF EXISTS `getUltimiMovimentiConto`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE  `getUltimiMovimentiConto`(IDCONTO INT(11) )
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT * FROM `map_transazioni` t WHERE t.idConto = IDCONTO ORDER BY idTransazione DESC;
END $$

DELIMITER ;



--
-- Definition of function `getMovimenti`
--

DROP PROCEDURE IF EXISTS `getMovimenti`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE  `getMovimenti`()
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT * FROM `map_transazioni` t ORDER BY idTransazione DESC;
END $$

DELIMITER ;


-----------
--- getStatistiche
--- Ritorna le statistiche di utilizzo
-------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `getStatistiche`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getStatistiche`()
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE NUMCONTI INT(11);
	DECLARE NUMCLIENTI INT(11);
	DECLARE NUMCONTICONFIDO INT(11);
	DECLARE NUMBANCOMAT INT(11);
	DECLARE NUMIMPIEGATI INT(11);
	SET NUMCONTI = (SELECT COUNT(*) FROM `map_conti` c WHERE c.fChiuso = FALSE);
	SET NUMCLIENTI = (SELECT COUNT(*) FROM `map_persone`);
	SET NUMCONTICONFIDO = (SELECT COUNT(*) FROM `map_fidi` f WHERE (SELECT isChiuso(f.idConto) ) IS FALSE AND f.fido > 0  );
	SET NUMBANCOMAT = (SELECT COUNT(*) FROM `map_bancomat` b WHERE (SELECT isChiuso(b.idConto) ) IS FALSE);
	SET NUMIMPIEGATI = (SELECT COUNT(*) FROM `map_impiegati`);
	SELECT 
		NUMCONTI as numeroConti,
		NUMCLIENTI as numeroClienti,
		NUMCONTICONFIDO as numeroFidi,
		(( NUMCONTICONFIDO / NUMCONTI ) * 100) as percentualeFidi,
		NUMBANCOMAT as numeroBancomat,
		NUMIMPIEGATI as numeroImpiegati;
END$$

DELIMITER ;



---------------------
--- getConti
--- Ritorna tutte le informazioni relative a tutti i conti (esclusi quelli chiusi)
-------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `getConti`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getConti`()
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT
		c.idConto as idConto,
		c.idPersona as idPersona,
		p.nome as intestatarioNome,
		p.cognome as intestatarioCognome,
		p.indirizzo as intestatarioIndirizzo,
		c.saldo as saldo,
		(SELECT f.fido as fido FROM `map_fidi` f WHERE c.idConto = f.idConto) as fido,
		(SELECT MAX(t.data) FROM `map_transazioni` t WHERE t.idConto = c.idConto) as ultimoMovimento
	FROM
		`map_conti` c JOIN (`map_persone` p) ON (c.idPersona = p.idPersona)
	WHERE
		c.fChiuso = FALSE;
		
END$$

DELIMITER ;





-----------
--- getBancomatDaId
--- Ritorna informazioni bancomat da IDAGENTE
-----------


DROP PROCEDURE IF EXISTS `getBancomatDaId`;

DELIMITER $$

CREATE PROCEDURE getBancomatDaId(IDBANCOMAT INT(11))
BEGIN
	SELECT 
		b.idBancomat as idBancomat,
		b.idConto as idConto,
		b.massimoPrelevabile as massimoPrelevabile
	FROM `map_bancomat` b WHERE  b.idBancomat = IDBANCOMAT;
END$$

DELIMITER ;




-----------
--- getBancomatConto
--- Ritorna informazioni bancomat da IDAGENTE
-----------


DROP PROCEDURE IF EXISTS `getBancomatConto`;

DELIMITER $$

CREATE PROCEDURE getBancomatConto(IDCONTO INT(11))
BEGIN
	SELECT 
		b.idBancomat as idBancomat,
		b.idConto as idConto,
		b.massimoPrelevabile as massimoPrelevabile
	FROM `map_bancomat` b WHERE  b.idConto = IDCONTO;
END$$

DELIMITER ;


--
-- Definition of function `chiudiConto`
-- chiude un conto indicato dall'id
--
DELIMITER $$

DROP PROCEDURE IF EXISTS `chiudiConto`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `chiudiConto`(IDCONTOCHIUDERE INT(11), IDAGENTE INT(11))
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	UPDATE `map_conti` c SET c.fChiuso = TRUE WHERE c.idConto = IDCONTOCHIUDERE;
	DELETE FROM `map_bancomat` WHERE idConto = IDCONTOCHIUDERE;
	DELETE FROM `map_agenti` WHERE ( idSpecifico = IDCONTOCHIUDERE AND tipoAgente = "CLIENTE" ) OR 
		(idSpecifico IN (SELECT b.idBancomat FROM map_bancomat b WHERE b.idConto = IDCONTOCHIUDERE) AND tipoAgente = "BANCOMAT");
	DELETE FROM `map_pinconti` WHERE idConto = IDCONTOCHIUDERE;
	CALL addTransazione(IDCONTOCHIUDERE, IDAGENTE, 0, CURDATE(), "CHIUSURA_CONTO");
END$$

DELIMITER ;





--
-- Definition of function `addConto`
--

DROP FUNCTION IF EXISTS `addConto`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `addConto`(IDPERSONA INT(11), SALDO FLOAT, FIDO FLOAT, DATA DATE, PIN VARCHAR(32), IDAGENTE INT(11)) RETURNS int(11)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ID INT(11);
	INSERT INTO map_conti (idPersona, saldo, fChiuso) VALUES
		(IDPERSONA, SALDO, 0);
	SET ID = (SELECT LAST_INSERT_ID());
	IF ( FIDO IS NOT NULL ) THEN
		INSERT INTO map_fidi (idConto, fido) VALUES
		(ID, FIDO);
	END IF;
	INSERT INTO map_pinconti (idConto, password) VALUES
		(ID, PIN);
	INSERT INTO map_agenti (idSpecifico, tipoAgente) VALUES 
		(ID, "CLIENTE");
	CALL addTransazione(ID, IDAGENTE, SALDO, DATA, "APERTURA_CONTO");
	RETURN ID;
END $$

DELIMITER ;



---------------------
--- getPersona
--- Ritorna tutte le informazioni relative alla persona
-------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `getPersona`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getPersona`(IDPERSONA INT(11) )
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT
		p.idPersona as idPersona,
		p.nome as nome,
		p.cognome as cognome,
		p.indirizzo as indirizzo
	FROM
		`map_persone` p
	WHERE
		p.idPersona = IDPERSONA;
		
END$$

DELIMITER ;



---------------------
--- getPersone
--- Ritorna tutte le informazioni relative alla persona
-------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `getPersone`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getPersone`()
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT
		p.idPersona as idPersona,
		p.nome as nome,
		p.cognome as cognome,
		p.indirizzo as indirizzo
	FROM
		`map_persone` p;
		
END$$

DELIMITER ;



--
-- Definition of function `addPersona`
--

DROP FUNCTION IF EXISTS `addPersona`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `addPersona`(NOME VARCHAR(255), COGNOME VARCHAR(255), INDIRIZZO VARCHAR(255)) RETURNS int(11)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ID int(11);
	INSERT INTO map_persone (`nome`,`cognome`,`indirizzo`) VALUE (NOME, COGNOME, INDIRIZZO);
	SET ID =  (SELECT MAX(idPersona) FROM map_persone p WHERE p.nome = NOME AND p.cognome = COGNOME AND p.indirizzo = INDIRIZZO);
	RETURN ID; 
END $$

DELIMITER ;


DROP FUNCTION IF EXISTS `addImpiegato`;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION  `addImpiegato`(NOME VARCHAR(50), PASSWORD VARCHAR(32), NOMEVISUALIZZATO VARCHAR(50)) RETURNS int(11)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ID INT(11);
	INSERT INTO map_impiegati (username, password, visualizzato) VALUES
	(NOME, PASSWORD, NOMEVISUALIZZATO); 
	SET ID = (SELECT MAX(idImpiegato) FROM map_impiegati i WHERE i.username = NOME AND i.password = PASSWORD AND i.visualizzato = NOMEVISUALIZZATO);
	INSERT INTO map_agenti (idSpecifico, tipoAgente) VALUES (ID, "IMPIEGATO");
	RETURN ID;
END $$

DELIMITER ;


--- Cerca i conti

DELIMITER $$

DROP PROCEDURE IF EXISTS `searchConti`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `searchConti`(NOME VARCHAR(255), COGNOME VARCHAR(255) )
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT
		c.idConto as idConto,
		c.idPersona as idPersona,
		p.nome as intestatarioNome,
		p.cognome as intestatarioCognome,
		p.indirizzo as intestatarioIndirizzo,
		c.saldo as saldo,
		(SELECT f.fido as fido FROM `map_fidi` f WHERE c.idConto = f.idConto) as fido,
		(SELECT MAX(t.data) FROM `map_transazioni` t WHERE t.idConto = c.idConto) as ultimoMovimento
	FROM
		`map_conti` c JOIN (`map_persone` p) ON (c.idPersona = p.idPersona)
	WHERE
		c.fChiuso = FALSE AND p.nome LIKE NOME AND p.cognome LIKE COGNOME; 
		
END$$

DELIMITER ;

--- cerca le persone

DELIMITER $$

DROP PROCEDURE IF EXISTS `searchPersone`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `searchPersone`(NOME VARCHAR(255), COGNOME VARCHAR(255) )
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT
		p.idPersona as idPersona,
		p.nome as nome,
		p.cognome as cognome,
		p.indirizzo as indirizzo
	FROM
		`map_persone` p
	WHERE
		p.nome LIKE NOME AND p.cognome LIKE COGNOME; 
		
END$$

DELIMITER ;

--- getContiPersona(INT)

DELIMITER $$

DROP PROCEDURE IF EXISTS `getContiPersona`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getContiPersona`(IDPERSONA INT(11) )
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT
		c.idConto as idConto,
		c.idPersona as idPersona,
		p.nome as intestatarioNome,
		p.cognome as intestatarioCognome,
		p.indirizzo as intestatarioIndirizzo,
		c.saldo as saldo,
		(SELECT f.fido as fido FROM `map_fidi` f WHERE c.idConto = f.idConto) as fido,
		(SELECT MAX(t.data) FROM `map_transazioni` t WHERE t.idConto = c.idConto) as ultimoMovimento
	FROM
		(SELECT * FROM `map_conti` cc WHERE cc.idPersona = IDPERSONA) as c JOIN (`map_persone` p) ON (c.idPersona = p.idPersona)
	WHERE
		c.fChiuso = FALSE;
		
END$$

DELIMITER ;


--- GetImpiegati

DELIMITER $$

DROP PROCEDURE IF EXISTS `getImpiegati`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getImpiegati`()
    READS SQL DATA
    DETERMINISTIC
BEGIN
	SELECT
		i.idImpiegato as idImpiegato,
		i.username as username,
		i.visualizzato as visualizzato
	FROM
		`map_impiegati` i;
		
END$$

DELIMITER ;


--- modifica la password di un impiegato

DELIMITER $$

DROP PROCEDURE IF EXISTS `modificaPasswordImpiegato`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `modificaPasswordImpiegato`(IDIMP INT(11), NUOVAPASSWORD VARCHAR(32))
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
		UPDATE `map_impiegati` SET `password` = NUOVAPASSWORD WHERE `idImpiegato` = IDIMP;
END$$

DELIMITER ;

-- cancella un impiegato

DELIMITER $$

DROP PROCEDURE IF EXISTS `rimuoviImpiegato`$$
CREATE  PROCEDURE `rimuoviImpiegato`(IDIMP INT(11) )
BEGIN
	DELETE FROM `map_impiegati` WHERE `idImpiegato` = IDIMP;
END$$

DELIMITER ;



--------------------
-- FINE NUOVE FUNZIONI #########################################################################################################
-------------------



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
