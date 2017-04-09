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
        conn.close()

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

        conn.commit()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e


def get_gallery_details(gallery_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_getGalleryDetails', (gallery_id,))

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


def add_new_artist(artist_name, description, file_name):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_insertArtist', (artist_name, description, file_name))

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message


def upload_artwork(art_name, desc, artist_id, file_name, gallery_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_uploadArt', (art_name, desc, artist_id, file_name, gallery_id))

        conn.commit()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message