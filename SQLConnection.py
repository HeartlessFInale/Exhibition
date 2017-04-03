import pymysql

def CreateConnection():
    try:
        conn = pymysql.connect(host='localhost', user='root', password='root', db='exhibition', cursorclass=pymysql.cursors.DictCursor)
        return conn

    except Exception as e:
        print e
        return e.message


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
        return e.message


def add_new_gallery(gallery_name, year, desc, photoBlob, latit, longit):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_insertGallery', (gallery_name, year, desc, photoBlob, latit, longit))

        CloseConnection(conn)

        return result

    except Exception as e:
        print e


def get_gallery_details(gallery_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_getGalleryDetails',(gallery_id,))

        result = {'gallery_detail':'', 'gallery_artist':'', 'gallery_art': ''}

        result_headers = ['gallery_detail', 'gallery_artist', 'gallery_art']

        for index, dataset in enumerate(cursor):
            result[result_headers[index]] = dataset
            cursor.nextset()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message
#getArtistList()
#addNewGallery('Gallery 2', 2010, 'Test Description', '', 40.7554826, -73.9968223)