import { Component, OnInit, RendererFactory2, Renderer2,ViewChild, TemplateRef } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/core/login/login.service';
import { LocalStorageService } from 'ngx-webstorage';
import { Account } from 'app/core/user/account.model';
import { BreakpointObserver } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'bmf-main',
  templateUrl: './main.component.html'
})
export class MainComponent implements OnInit {
  private renderer: Renderer2;
  account: Account | null = null;
  gtMd!: Observable<boolean>;
  isGtMd = true;
   @ViewChild('searchBar')
    searchBar!: TemplateRef<any>;
  dialogRef: any;
   totalComplains = '0';
    inProduction: boolean | undefined = false;
    isSearchBar = false;

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private translateService: TranslateService,
    rootRenderer: RendererFactory2,
    private localStorage: LocalStorageService,
    private loginService: LoginService,
    private breakPointObsever: BreakpointObserver

  ) {
    this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
    this.gtMd = this.breakPointObsever.observe('(max-width: 959px)').pipe(map(result => !result.matches));
    this.gtMd.subscribe(value => {
      this.isGtMd = value;
    });
  }

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });

    this.translateService.onLangChange.subscribe((langChangeEvent: LangChangeEvent) => {
      this.updateTitle();

      this.renderer.setAttribute(document.querySelector('html'), 'lang', langChangeEvent.lang);
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }

    isAuthenticated(): boolean {
      return this.accountService.isAuthenticated();
    }

    login(): void {
      this.loginService.login();
    }


  logout(): void {
    this.loginService.logout();
    this.router.navigate(['']);
  }
}
