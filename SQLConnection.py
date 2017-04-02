import pymysql

def CreateConnection():
    try:
        conn = pymysql.connect(host='localhost', user='root', password='root', db='exhibition')
        return conn

    except Exception as e:
        print e


def CloseConnection(conn):
    try:
        conn.Close()

    except Exception as e:
        print e

def getArtistList():
    try:

        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.execute('SELECT gallery_id,name,photo,description,latitude,longitude FROM gallery ')

        result = cursor.fetchall()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e


def addNewGallery(galleryName,year,desc,photoBlob,latit,longit):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_insertGallery', (galleryName, year, desc, photoBlob, latit, longit))
        #sql = 'CALL `exhibition`.`sp_insertGallery`(<{IN p_name VARCHAR (128)}>, <{IN p_year INT}>, <{IN p_desc VARCHAR (1024)}>, <{IN p_photo BLOB}>, <{IN p_lat DOUBLE}>, <{IN p_long DOUBLE}>);'
        #cursor.execute(sql)
        pass

    except Exception as e:
        print e

#getArtistList()
#addNewGallery('Gallery 2', 2010, 'Test Description', '', 40.7554826, -73.9968223)