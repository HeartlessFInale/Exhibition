import pymysql

def CreateConnection():
    try:
        conn = pymysql.connect(host='localhost', user='root', password='root', db='exhibition', cursorclass=pymysql.cursors.DictCursor)
        return conn

    except Exception as e:
        print e


def CloseConnection(conn):
    try:
        conn.Close()

    except Exception as e:
        print e


def get_gallery_list(artist_id):
    try:

        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_getGalleryList', (artist_id,))

        result = cursor.fetchall()
        CloseConnection(conn)

        return result

    except Exception as e:
        print e


def add_new_gallery(gallery_name, year, desc, photoBlob, latit, longit):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_insertGallery', (gallery_name, year, desc, photoBlob, latit, longit))

        return result

    except Exception as e:
        print e

#getArtistList()
#addNewGallery('Gallery 2', 2010, 'Test Description', '', 40.7554826, -73.9968223)