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
            if len(cursor._rows) == 1 and index == 0:
                rows_set = cursor._rows[0]
            else:
                rows_set = cursor._rows
            result[result_headers[index]] = rows_set
            #test = cursor.fetchall()
            cursor.nextset()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message


def get_artist_details(artist_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_getArtistDetails', (artist_id,))

        result = {'artist_detail': '', 'artworks': ''}

        result_headers = ['artist_detail', 'artworks']

        for index, dataset in enumerate(cursor):
            if len(cursor._rows) == 1 and index == 0:
                rows_set = cursor._rows[0]
            else:
                rows_set = cursor._rows
            result[result_headers[index]] = rows_set
            # test = cursor.fetchall()
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

        conn.commit()

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