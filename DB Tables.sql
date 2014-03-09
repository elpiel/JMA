CREATE TABLE categories (
  cat_id INT(10) IDENTITY(1,1) NOT NULL,
  cat_name NVARCHAR(255) NOT NULL, -- to use UTF-8 we use nvarchar
  PRIMARY KEY (cat_id) -- our primary key
);

CREATE TABLE movies (
  -- IDENTITY(1,1) - start from 1 and increment by 1
  m_id INT(10) IDENTITY(1,1) NOT NULL,
  m_name NVARCHAR(255) NOT NULL,
  m_trailer VARCHAR(255), -- we don't need utf-8 for URLs
  m_desc TEXT,
  m_date DATE, -- the premier date of the movie

  PRIMARY KEY(m_id)
);

INSERT INTO MOVIES VALUES (NULL, 'Страшен Филм', 'youtube.com', 'Описание', '2007-06-28');

CREATE TABLE movies_categories (
  m_id INT(10) NOT NULL,
  cat_id INT(10) NOT NULL,

  -- On movie delete delete here too
  CONSTRAINT fk_mc_movies FOREIGN KEY ( m_id )
  REFERENCES movies ( m_id ) ON DELETE CASCADE,

  CONSTRAINT fk_mc_categories FOREIGN KEY ( cat_id )
  REFERENCES categories ( cat_id ),

  PRIMARY KEY (m_id, cat_id)

);

INSERT INTO MOVIES_CATEGORIES VALUES (1, 1);
INSERT INTO MOVIES_CATEGORIES VALUES (1, 3);


INSERT INTO categories VALUES ( null, 'Ужаси');
INSERT INTO categories VALUES ( null, 'Анимация');
INSERT INTO categories VALUES ( null, 'Комедия');
