INSERT INTO USER (LOGIN_NAME, PASSWORD, USER_NAME, STATUS,
                  EXPIRED_DATE, PASSWORD_EXPIRED_DATE) VALUES
-- username : admin   password : admin
('admin', 'XehbqLyy59Q=', 'مدیر', 1, CURDATE()+ INTERVAL 30 DAY,CURDATE()+ INTERVAL 30 DAY),
-- username : user   password : admin
('user', 'XehbqLyy59Q=', 'کاربر', 1, CURDATE()+ INTERVAL 30 DAY,CURDATE()+ INTERVAL 30 DAY);

INSERT INTO ROLE(ROLE_TITLE,ROLE_TYPE, STATUS) VALUES
('مدیر', 1, 1),
('کاربر', 1, 1);

INSERT INTO USER_ROLE(CREATOR_USER_ID, ROLE_ID, USER_ID, STATUS) VALUES
(1, 1, 1, 1),
(1, 2, 1, 1),
(1, 2, 2, 1);

INSERT INTO WIDGET_MENU(STATUS,MENU_TYPE,MENU_ORDER,WIDGET_ICON,
                        WIDGET_PATH,MENU_NAME_FA,CREATOR_USER_ID,PARENT_ID, PARENT_MENU_NAME_FA) VALUES
(1, 1, 1, NULL, NULL, 'مدیریت سامانه',1, NULL, NULL),
(1, 2, 1, 'fa fa-user-plus', 'management/user', 'کاربران سیستم',null, 1, 'مدیر سامانه'),
(1, 2, 2, 'fa fa-users', 'management/role', 'نقش های سیستم',1, 1, 'مدیر سامانه'),
(1, 2, 3, 'fa fa-tasks', 'management/menu', 'منوهای سیستم',1, 1, 'مدیر سامانه'),
(1, 2, 4, 'fa fa-share-alt', 'management/menu-role', 'تخصيص منو به نقش',1, 1, 'مدیر سامانه'),
(1, 2, 5, 'fa fa-share-alt', 'management/role-user', 'تخصيص نقش به كاربر',1, 1, 'مدیر سامانه');

INSERT INTO ROLE_WIDGET(WIDGET_MENU_ID,ROLE_ID,STATUS,CREATOR_USER_ID) VALUES
(1,1,1,1),
(2,1,1,1),
(3,1,1,1),
(4,1,1,1),
(5,1,1,1),
(6,1,1,1);