-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Hostiteľ: localhost
-- Čas generovania: So 25.Mar 2023, 00:27
-- Verzia serveru: 10.5.18-MariaDB-0+deb11u1
-- Verzia PHP: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáza: `dvestodola`
--

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `Inzerat`
--

CREATE TABLE `Inzerat` (
  `id_inzeratu` int(100) NOT NULL,
  `nazov` varchar(100) NOT NULL,
  `opis` text NOT NULL,
  `id_cena` int(100) NOT NULL,
  `id_kategorie` int(100) NOT NULL,
  `id_podkategorie` int(100) NOT NULL,
  `id_podpodkategorie` int(100) NOT NULL,
  `id_kontaktu` int(100) NOT NULL,
  `id_okresu` int(7) NOT NULL,
  `klucove_slova` varchar(64) NOT NULL,
  `datum_pridania` date NOT NULL,
  `id_obrazku` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Sťahujem dáta pre tabuľku `Inzerat`
--

INSERT INTO `Inzerat` (`id_inzeratu`, `nazov`, `opis`, `id_cena`, `id_kategorie`, `id_podkategorie`, `id_podpodkategorie`, `id_kontaktu`, `id_okresu`, `klucove_slova`, `datum_pridania`, `id_obrazku`) VALUES
(1, 'DDR4 128GB ECC Server 2933mhz', 'Predám 2x 64gb DDR4 modul s ECC\r\nvhodný do serverov či iných PC ktoré podporujú ECC pamäte\r\npn: MTA36ASF8G72PZ-2G9B2\r\ncena za ks: 250€\r\ncena spolu: 400€\r\n', 33, 8, 16, 22, 1, 3, '', '2023-03-13', 2),
(2, 'Predam horsky bicykel', 'Predám úplne nový horský bicykel a fatbike cena je od viac informácií po tel', 38, 13, 23, 39, 2, 40, 'Bycikel', '2023-01-20', 3),
(3, 'Škótske mačiatka s PP/REZERVACIA', 'REZERVACIA*\r\nCHS ponuka mačiatok s PP/F.TICA\r\nRodicia sú testované na FIV FeLV a genetické choroby s negativným vysledkom.\r\nMáme 2 dievča:\r\n1.Scottish straight mramorová silver chocolate\r\n2.Scottish fold chocolate\r\nV čase odberu mačiatka budu očkovane odčervene s vet preukazom a mikročipom+ PP/ rodokmeň\r\nTak tiež budu naučené na wc a škrabadlo, plne socializované.\r\nDo odberú - na začiatok Aprila\r\nZmluva je samozrejmosťou\r\nPodrobnijšie inf.ziskate na t.č.\r\n421907240592/Viber/WhatsApp\r\nMôžno doprava v ramci Slovenska', 39, 1, 1, 5, 3, 17, 'Free maciatka, maciatka zadarmo', '2022-12-10', 4),
(4, 'Škoda Superb 1.4 TSI 150PS ACT Sportline Webasto 2018', 'Palivo: Benzín\r\nKaroséria: Sedan\r\nRok výroby: 12/2017 Model 2018\r\nPrevodovka: Manuálna - 6 stupňov\r\nObjem valcov: 1 395 cm3\r\nVýkon: 110kW (150PS)\r\nNajazdené: 225 230 km\r\nPohon: Predný\r\nPočet dverí: 5 (5 miest)\r\nFarba: Biela\r\nSpotreba: 4.9 l\r\nKombinovaná spotreba: 7 l\r\nV meste: 4.3 l\r\nStav vozidla: Dovezené,Garážované,Možný leasing,Možný úver,Nebúrané,Prvý majiteľ,Servisná knižka,TOP stav,Úplná servisná história,\r\nVýbava:\r\nHISTORIA: DOVOZ ŠVAJČIARSKO..\r\nPoznámka:\r\nAUTO JE VYBORNOM TECHNICKOM STAVE AKO NOVE TREBA VIDIET A VYSKUŠAT,MOŽNA SERIOZNA DOHODA\r\n', 39, 4, 5, 42, 4, 4, '', '2023-03-01', 5),
(5, 'Predam tieto 14k sperky a aj zlomkove zlato', 'Predam tieto 14k sperky a aj zlomkove zlato. Skuste poslat ponuku.', 22, 18, 32, 37, 5, 5, 'Sperky', '2023-03-01', 6),
(6, 'Ponukam Pravdu', 'Ponukam pravdu, vydania 2005 - 2009 (este pred tym nez to s nimi slo dole vodou). Kto sa neboji, nech pride na ulicu Kuzmanyho v Krupine, ved my si prideme na svoje :)\r\n\r\nPravda boli, no vitazi.', 30, 0, 36, 37, 6, 5, 'Pravda', '2022-06-08', 1),
(7, 'Apple iPhone 11 Pro Space Gray 64GB', 'Predám Apple iPhone 11 Pro 64GB Space Gray.\r\nTelefón je bez škrabancov, odhlásený z iCloudu a neblokovaný na žiadnu sieť.\r\niPhone je vo veľmi peknom a max zachovalom stave.,bol nosený v ochrannom obale a na displeji bolo vždy nalepené kvalitné ochranné tvrdené sklíčko(Gorilla Glass).\r\nNa displeji nie sú žiadne škrabance ani iné poškodenia.\r\nRám spolu s telom iPhonu sú v peknom a zachovalom stave.\r\nZariadenie je plne funkčné bez skrytých vád a chýb.\r\nZdravie batérie je 98%. Face ID je plne funkčné.\r\nKu zariadeniu sú nové ochranné silikónové obaly,ktoré neboli nikdy použité.\r\nTelefón je odblokovaný na všetky siete. Všetky účty sú z iPhonu odhlásené.\r\nMá kompletné balenie, krabica, brožúrky, nabíjací kábel.\r\nPreferujem osobný odber,alebo možem poslať 1-triedou a do 24 hodín máte doma.\r\n', 31, 9, 38, 44, 7, 68, '', '2022-12-25', 7),
(8, 'Deus Ex Mankind Divided', 'Hra Deus Ex Mankind Divided\r\nMožný osobný odber v MT a po dohode aj v KE.\r\n', 30, 8, 19, 27, 8, 74, '', '2023-03-04', 8),
(9, 'case 590 sr', 'prodam traktorbagr case 590 SR , rok 2007, cca8500mth, vyvody svahovka, vyvody kladivo, automaticka prevodovka, joystickove ovladani, velmi slusny stav, minimalni vůle, lzice 40,80, hydraulicka svahovka…vice info po tel.\r\ncena bez DPH možnost dopravy\r\n', 35, 6, 40, 46, 9, 80, '', '2023-01-12', 9),
(10, 'Lely Welger RP 445 svinovací lis na kulaté balíky', 'Prodám lis na kulaté balíky Lely Welger RP 445 v TOP STAVU. Rok výroby 2013. 23055 balíků, řezání, odklápěcí dno, vzduchové brzdy. Cena k jednání 639 000 Kč bez DPH. Při rychlém jednání sleva. ', 36, 6, 41, 47, 10, 81, '', '2023-03-13', 10);

--
-- Kľúče pre exportované tabuľky
--

--
-- Indexy pre tabuľku `Inzerat`
--
ALTER TABLE `Inzerat`
  ADD PRIMARY KEY (`id_inzeratu`),
  ADD KEY `id_cena` (`id_cena`),
  ADD KEY `id_kategorie` (`id_kategorie`),
  ADD KEY `id_podkategorie` (`id_podkategorie`),
  ADD KEY `id_podpodkategorie` (`id_podpodkategorie`),
  ADD KEY `id_kontaktu` (`id_kontaktu`),
  ADD KEY `id_okresu` (`id_okresu`),
  ADD KEY `id_obrazku` (`id_obrazku`);

--
-- AUTO_INCREMENT pre exportované tabuľky
--

--
-- AUTO_INCREMENT pre tabuľku `Inzerat`
--
ALTER TABLE `Inzerat`
  MODIFY `id_inzeratu` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Obmedzenie pre exportované tabuľky
--

--
-- Obmedzenie pre tabuľku `Inzerat`
--
ALTER TABLE `Inzerat`
  ADD CONSTRAINT `Inzerat_ibfk_1` FOREIGN KEY (`id_cena`) REFERENCES `Cena` (`id_cena`),
  ADD CONSTRAINT `Inzerat_ibfk_2` FOREIGN KEY (`id_kategorie`) REFERENCES `Kategoria` (`id_kategorie`),
  ADD CONSTRAINT `Inzerat_ibfk_3` FOREIGN KEY (`id_podkategorie`) REFERENCES `Podkategoria` (`id_podkategorie`),
  ADD CONSTRAINT `Inzerat_ibfk_4` FOREIGN KEY (`id_podpodkategorie`) REFERENCES `Podpodkategoria` (`id_podpodkategorie`),
  ADD CONSTRAINT `Inzerat_ibfk_5` FOREIGN KEY (`id_kontaktu`) REFERENCES `Kontakt` (`id_kontaktu`),
  ADD CONSTRAINT `Inzerat_ibfk_6` FOREIGN KEY (`id_okresu`) REFERENCES `Okres` (`id_okresu`),
  ADD CONSTRAINT `Inzerat_ibfk_7` FOREIGN KEY (`id_obrazku`) REFERENCES `Obrazok` (`id_obrazku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
