import sqlalchemy.exc
from sqlalchemy import delete, update, create_engine, Column, String, Integer, Boolean
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from contextlib import contextmanager
from tempfile import mktemp
from platform import system

temp_db = mktemp(suffix='.sqlite')
conn_uri_handler = \
{
    'Windows': f'sqlite:///{temp_db}',
    'Darwin': f'sqlite:////{temp_db}',
    'Linux': f'sqlite:////{temp_db}',
    'Java': f'sqlite:////{temp_db}'
}

engine = create_engine(conn_uri_handler[system()])
Base = declarative_base(bind=engine)

class Book(Base):
    __tablename__ = 'książki'
    id = Column(Integer, primary_key = True)
    title = Column('tytuł', String(50))
    author = Column('autor', String(40))
    occup = Column('wypożyczona', Boolean, default=False)

Base.metadata.create_all()
Session = sessionmaker(bind=engine)

@contextmanager
def create_session():
    session = Session()
    try:
        yield session
        session.commit()
    except Exception:
        session.rollback()
        raise
    finally:
        session.close()

def provide_session(func):
    def wrapper(*args, **kwargs):
        with create_session() as session:
            args = (*args, session) if args else (session,)
            return func(*args, **kwargs)
    return wrapper

###############<<<OPCJE MENU>>>################

@provide_session
def showRecords(session):
    print("\n\x1B[4mID\tTytuł"," "*45,"Autor"," "*35,"Czy wypożyczona\x1B[0m", sep="")
    spc1 = " "*50
    spc2 = " "*40
    for record in session.query(Book).all():
        prt1 = record.title + spc1[len(record.title):]
        prt2 = record.author + spc2[len(record.author)+1:]
        print(f"{record.id}\t{prt1}{prt2}", ("nie" if record.occup is False else "tak"))

@provide_session
def addRecord(session):
    title = input("\nPodaj tytuł książki: ")
    try:
        assert len(title) >= 1
        author = input("Podaj autora książki: ")
        i = 1
        for book in session.query(Book).all():
            if i != book.id:
                break
            else:
                i += 1
        session.add(Book(id=i, title=title, author=author))
        print("\nKsiążka dodana do bazy!")
    except AssertionError:
        print("\nKsiążka musi posiadać tytuł!")

@provide_session
def deleteRecord(session):
    if len(session.query(Book).all()) == 0:
        print("\nBaza nie posiada żadnych rekordów!")
    else:
        try:
            showRecords()
            x = int(input("Podaj ID rekordu do usunięcia: "))
            try:
                assert type(Book()) == type(session.query(Book).filter_by(id=x).first())
                session.query(Book).filter_by(id = x).delete()
                print("Rekord został usunięty.\n")
            except AssertionError:
                print("Nie istnieje rekord o takim ID!")
        except ValueError:
            print("ID rekordu musi być typu całkowitego!")

@provide_session
def updateRecord(session):
    dict = {
        "tytuł": "title",
        "autor": "author",
        "wypożyczenie": "occup"
    }
    trfl = {
        "tak": True,
        "nie": False
    }
    if len(session.query(Book).all()) == 0:
        print("\nBaza nie posiada żadnych rekordów!")
    else:
        try:
            showRecords()
            x = int(input("\nPodaj ID rekordu, który chcesz zaktualizować: "))
            try:
                assert type(Book()) == type(session.query(Book).filter_by(id = x).first())
                try:
                    field_pl = input("Podaj pole, które chcesz zmienić (tytuł, autor, wypożyczenie): ")
                    field_eng = dict[field_pl]
                    new_value = input(f"Podaj nową wartość pola <{field_pl}>: ")
                    if field_eng == "occup":
                        try:
                            new_value = trfl[new_value]
                        except KeyError:
                            print("\nPole przyjmuje tylko wartości <tak> lub <nie>!")
                    session.query(Book).filter_by(id=x).update({field_eng: new_value})
                    print("\nRekord zaktualizowany!")
                except KeyError:
                    print("\nTakie pole nie istnieje!")
            except AssertionError:
                print("\nNie istnieje rekord o takim ID!")
        except ValueError:
            print("\nID rekordu musi być typu całkowitego i nie może być puste!")

x = 0
while x != 5:
    print("\n1. Wyświetl bazę.\n2. Dodaj rekord.\n3. Usuń rekord.\n4. Edytuj rekord\n5. Wyjdź.\n>>", end=" ")
    try:
        x = int(input())
        if x == 1:
            showRecords()
        elif x == 2:
            addRecord()
        elif x == 3:
            deleteRecord()
        elif x == 4:
            updateRecord()
        elif x >= 6 or x <= 0:
            raise ValueError
    except ValueError:
        print("\nPodaj poprawną liczbę całkowitą!")