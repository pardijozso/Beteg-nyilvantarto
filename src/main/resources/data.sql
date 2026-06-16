INSERT INTO doctor (name)
VALUES ('Dr. Kovács Anna');

INSERT INTO doctor (name)
VALUES ('Dr. Nagy Péter');

INSERT INTO doctor (name)
VALUES ('Dr. Kiss Jenő');

INSERT INTO doctor (name)
VALUES ('Dr. Oros Panna');

INSERT INTO doctor (name)
VALUES ('Dr. Dékány László');

INSERT INTO patients (name, birth_place, birth_date, mother_name, address, diagnosis, doctor_id)
VALUES
    -- 1-es számú orvos betegei (4 beteg)
    ('Kovács János', 'Budapest', '1985-04-12', 'Szabó Mária', '1051 Budapest, Erzsébet tér 4.', 'Megfázás', 1),
    ('Kiss Elena', 'Debrecen', '1993-11-23', 'Nagy Ilona', '4024 Debrecen, Piac utca 12.', 'Csuklótörés', 1),
    ('Tóth Gábor', 'Szeged', '1978-08-05', 'Kelemen Anna', '6720 Szeged, Klauzál tér 2.', 'Rándult boka', 1),
    ('Szabó Bence', 'Miskolc', '2001-05-14', 'Varga Edit', '3525 Miskolc, Széchenyi utca 10.', 'Akut bronchitis', 1),

    -- 2-es számú orvos betegei (2 beteg)
    ('Horváth Beatrix', 'Győr', '2002-02-17', 'Fekete Erzsébet', '9021 Győr, Baross Gábor út 8.', 'Inzulinrezisztencia', 2),
    ('Németh Zoltán', 'Pécs', '1965-06-30', 'Varga Katalin', '7621 Pécs, Király utca 15.', 'Magas vérnyomás', 2),

    -- 3-as számú orvos betegei (2 beteg)
    ('Molnár Péter', 'Eger', '1990-01-01', 'Mrúz Tímea', '3300 Eger, Dobó tér 1.', 'Kötőhártya-gyulladás', 3),
    ('Balogh Dóra', 'Kecskemét', '1995-09-08', 'Kovács Judit', '6000 Kecskemét, Kossuth tér 3.', 'Migrén', 3),

    -- 4-es számú orvos betegei (1 beteg)
    ('Farkas Dániel', 'Nyíregyháza', '1988-03-22', 'Simon Olga', '4400 Nyíregyháza, Hősök tere 2.', 'Gyomorrontás', 4),

    -- 5-ös számú orvos betegei (1 beteg)
    ('Papp Viktória', 'Szombathely', '1997-12-05', 'Takács Kinga', '9700 Szombathely, Fő tér 5.', 'Fülgyulladás', 5);

    INSERT INTO notes (content, created_at, patient_id, doctor_id)
    VALUES
        -- 1-es beteg jegyzete (Kovács János - Megfázás)
        ('NeoCitran felírva. 3 nap ágynyugalom, sok meleg tea fogyasztása javasolt. Kontroll szükség esetén.', CURRENT_TIMESTAMP, 1, 1),

        -- 2-es beteg jegyzete (Kiss Elena - Csuklótörés)
        ('Gipszelés megtörtént. Fájdalomcsillapító (Cataflam) felírva szükség esetére. 6 hét múlva kontroll és röntgen.', CURRENT_TIMESTAMP, 2, 1),

        -- 3-as beteg jegyzete (Tóth Gábor - Rándult boka)
        ('Fáslizás, pihentetés, borogatás javasolt. Lioton gél napi 3x. Terhelést egy hétig kerülni kell.', CURRENT_TIMESTAMP, 3, 1),

        -- 4-es beteg jegyzete (Szabó Bence - Akut bronchitis)
        ('Fromilid antibiotikum felírva (1x1, 7 napig). Ambroxol köptető napi 3x. Bőséges folyadékbevitel.', CURRENT_TIMESTAMP, 4, 1),

        -- 5-ös beteg jegyzete (Horváth Beatrix - Inzulinrezisztencia)
        ('Laborleletek kiértékelve. 160g-os szénhidrát diéta és rendszeres mozgás előírva. Meforal 500mg felírva.', CURRENT_TIMESTAMP, 5, 2),

        -- 6-os beteg jegyzete (Németh Zoltán - Magas vérnyomás)
        ('Coverex AS tabletta beállítva reggelente. Napi vérnyomásnapló vezetése kötelező. Sómentes diéta.', CURRENT_TIMESTAMP, 6, 2),

        -- 7-es beteg jegyzete (Molnár Péter - Kötőhártya-gyulladás)
        ('Tobrex szemcsepp felírva, napi 4x1 csepp mindkét szembe, 5 napig. Számítógép használat mérséklése.', CURRENT_TIMESTAMP, 7, 3),

        -- 8-as beteg jegyzete (Balogh Dóra - Migrén)
        ('Fretasan rohamoldó gyógyszer felírva. Sötét, csendes szobában pihenés a rohamok alatt. Koffein kerülendő.', CURRENT_TIMESTAMP, 8, 3),

        -- 9-es beteg jegyzete (Farkas Dániel - Gyomorrontás)
        ('Könnyű diéta (háztartási keksz, főtt krumpli) 2 napig. Normaflore napi 2x1 bélflóra helyreállításra.', CURRENT_TIMESTAMP, 9, 4),

        -- 10-es beteg jegyzete (Papp Viktória - Fülgyulladás)
        ('Otipax fülcsepp felírva, napi 3x2 csepp. A fület melegen kell tartani (sapka vagy fülmelegítő), víz nem érheti.', CURRENT_TIMESTAMP, 10, 5);