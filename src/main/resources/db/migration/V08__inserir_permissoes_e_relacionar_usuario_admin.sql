INSERT INTO permissao VALUES(1,'ROLE_CADASTRAR_CIDADE');
INSERT INTO permissao VALUES(2,'ROLE_CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (CODIGO_GRUPO,CODIGO_PERMISSAO) VALUES(1,1);
INSERT INTO grupo_permissao (CODIGO_GRUPO,CODIGO_PERMISSAO) VALUES(1,2);

INSERT INTO usuario_grupo (CODIGO_USUARIO,CODIGO_GRUPO) VALUES (
(SELECT codigo FROM usuario WHERE email = 'admin@brewer.com'),1);
