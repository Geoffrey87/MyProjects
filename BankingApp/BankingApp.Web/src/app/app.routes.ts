import { Routes } from '@angular/router';
import { authGuard, roleGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./features/auth/register/register').then((m) => m.RegisterComponent),
  },

  {
    path: '',
    loadComponent: () => import('./shared/components/shell/shell').then((m) => m.ShellComponent),
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard').then((m) => m.Dashboard),
      },
      {
        path: 'accounts',
        loadComponent: () =>
          import('./features/accounts/account-list/account-list').then((m) => m.AccountList),
      },
      {
        path: 'accounts/:id',
        loadComponent: () =>
          import('./features/accounts/account-detail/account-detail').then((m) => m.AccountDetail),
      },
      {
        path: 'cards',
        loadComponent: () => import('./features/cards/card-list/card-list').then((m) => m.CardList),
      },
      {
        path: 'loans',
        loadComponent: () => import('./features/loans/loan-list/loan-list').then((m) => m.LoanList),
      },
      {
        path: 'services',
        loadComponent: () =>
          import('./features/services/service-list/service-list').then((m) => m.ServiceList),
      },
      {
        path: 'notifications',
        loadComponent: () =>
          import('./features/notifications/notification-list/notification-list').then(
            (m) => m.NotificationList,
          ),
      },
      {
        path: 'admin/users',
        canActivate: [roleGuard('Admin')],
        loadComponent: () =>
          import('./features/admin/user-management/user-management').then((m) => m.UserManagement),
      },
      {
        path: 'admin/loans',
        canActivate: [roleGuard('Admin')],
        loadComponent: () =>
          import('./features/admin/loan-approval/loan-approval').then((m) => m.LoanApproval),
      },
      {
        path: 'admin/cards',
        canActivate: [roleGuard('Admin')],
        loadComponent: () =>
          import('./features/admin/card-approval/card-approval').then((m) => m.CardApproval),
      },
    ],
  },

  {
    path: '**',
    loadComponent: () => import('./shared/components/not-found/not-found').then((m) => m.NotFound),
  },
];
