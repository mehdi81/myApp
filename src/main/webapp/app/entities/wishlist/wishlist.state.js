(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('wishlist', {
            parent: 'entity',
            url: '/wishlist?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myApp.wishlist.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wishlist/wishlists.html',
                    controller: 'WishlistController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('wishlist');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('wishlist-detail', {
            parent: 'entity',
            url: '/wishlist/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myApp.wishlist.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wishlist/wishlist-detail.html',
                    controller: 'WishlistDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('wishlist');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Wishlist', function($stateParams, Wishlist) {
                    return Wishlist.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'wishlist',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('wishlist-detail.edit', {
            parent: 'wishlist-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wishlist/wishlist-dialog.html',
                    controller: 'WishlistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Wishlist', function(Wishlist) {
                            return Wishlist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wishlist.new', {
            parent: 'wishlist',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wishlist/wishlist-dialog.html',
                    controller: 'WishlistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                creationDate: null,
                                hidden: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('wishlist', null, { reload: 'wishlist' });
                }, function() {
                    $state.go('wishlist');
                });
            }]
        })
        .state('wishlist.edit', {
            parent: 'wishlist',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wishlist/wishlist-dialog.html',
                    controller: 'WishlistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Wishlist', function(Wishlist) {
                            return Wishlist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wishlist', null, { reload: 'wishlist' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wishlist.delete', {
            parent: 'wishlist',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wishlist/wishlist-delete-dialog.html',
                    controller: 'WishlistDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Wishlist', function(Wishlist) {
                            return Wishlist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wishlist', null, { reload: 'wishlist' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
